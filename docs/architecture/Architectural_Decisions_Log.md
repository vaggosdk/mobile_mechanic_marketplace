# Architecture Decisions Log (ADL)

This document records the global technology stack and significant architectural decisions made during the development of the Mobile Mechanic Marketplace backend, including their context, justification, and consequences.

---

# Global Technology Stack & Environment

To achieve the primary goal of building a robust, backend-first MVP, the following core technologies have been explicitly chosen for the system's foundational architecture:

* **Language & Runtime:** Java (LTS)
* **Application Framework:** Spring Boot (Spring Data JPA, Spring Security, Spring Web)
* **Primary Database:** PostgreSQL (with PostGIS extension enabled)
* **Database Migration & Versioning:** Liquibase
* **Local Development Environment:** Containerized via Docker / Docker Compose

---

# Architecture Decision Records (ADRs)

## ADR 1: Spatial Storage and Matching Logic via PostGIS
* **Status:** Approved
* **Context:** The platform requires matching customer service requests with mechanics based on geographic availability radii. Because this is a backend-first MVP, we need a highly efficient, relational way to handle spatial queries without building or relying on heavy real-time GPS streaming infrastructure.
* **Decision:** We will enable and use the **PostGIS** extension on top of PostgreSQL. Location coordinates (mechanic base locations and customer incident snapshots) will be stored using the native `GEOMETRY(Point, 4326)` data type, utilizing the standard WGS 84 GPS coordinate system.
* **Consequences:**
    * **Pros:** Allows lightning-fast proximity matching using native database functions like `ST_DWithin` and `ST_Distance` cast to `geography`.
    * **Pros:** Can be indexed using a **GiST (Generalized Search Tree)** spatial index to ensure $O(\log N)$ lookup performance as the database scales.
    * **Cons:** Introduces a hard dependency on the PostGIS extension, meaning local development environments (such as Docker setups) must use a PostGIS-enabled PostgreSQL image. Coordinates must strictly be handled in `(Longitude, Latitude)` order across all database inputs.

---

## ADR 2: Media Storage Layer & Decoupled Architecture
* **Status:** Approved
* **Context:** The product vision requires customers to upload dashboard warning light photos, OBD application screenshots, and short videos to describe vehicle symptoms. Storing raw binary files (`BLOB` or `BYTEA`) directly inside a relational database causes massive table bloat, degrades transactional performance, and significantly complicates database backup windows.
* **Decision:** Adopt a two-layer storage strategy that completely decouples raw binary assets from the transactional database:
    1.  **Physical Storage Layer:** Files will live in an Object Storage Service. For local development, this will be a designated directory or a local **MinIO** container. For production, it will seamlessly swap to cloud object storage (like AWS S3 or Google Cloud Storage).
    2.  **Metadata Layer (PostgreSQL):** The database will only store a lightweight text URL string pointing to the object storage path (e.g., `request_attachments.file_url`).
* **Consequences:**
    * **Pros:** Keeps PostgreSQL fast, lean, and optimized for transactional data. Database backups remain highly portable and quick to execute.
    * **Cons:** Requires the application layer (Spring Boot) to coordinate a multi-step upload process (saving the file to storage first, then writing the string URL reference to the database). Broken links can occur if a file is deleted from object storage without updating the database records.

---

## ADR 3: Normalization of Diagnostic Data
* **Status:** Approved
* **Context:** A single customer service request can contain *multiple* photos, videos, and manual Diagnostic Trouble Codes (DTCs like `P0420`, `P0301`) simultaneously. Storing these inside a single `service_requests` table row would lead to rigid, highly constrained table columns or messy, hard-to-query JSON blobs.
* **Decision:** Normalize diagnostic and media data into separate, specialized tables linked back via foreign keys: `request_attachments` and `request_obd_codes`.
* **Consequences:**
    * **Pros:** Clean, normalized relational database design that permits a customer to attach an infinite (or application-capped) number of diagnostics to a single request without creating ultra-wide, slow-to-scan database rows.
    * **Cons:** Requires SQL table joins (`JOIN`) when fetching a comprehensive summary of a service request for a mechanic's dashboard.

---

## ADR 4: Strict One-to-One Mechanic-to-Request Assignment
* **Status:** Approved
* **Context:** The marketplace concept dictates that while multiple mechanics might be *notified* about a nearby breakdown, exactly one specialist can ultimately claim, diagnose, and handle a specific job thread.
* **Decision:** Model the assigned mechanic directly as a nullable foreign key (`service_requests.specialist_id`) pointing back to the `entities.service_provider` table.
* **Consequences:**
    * **Pros:** Natively enforces a strict one-to-one mapping at the database layer once a job is claimed. The column remains `NULL` when a customer initializes an on-demand request, indicating it is open to matching mechanics, and updates to the mechanic's ID the exact moment they accept it.
    * **Cons:** If a mechanic drops a job and another needs to take over, this column must be explicitly updated, requiring state transition safeguards in our application logic.

---

## ADR 5: Request Lifecycle Immutability
* **Status:** Approved
* **Context:** When requests are canceled, timed out, or rejected, we need to preserve history for analytics, auditing, and platform trust metrics.
* **Decision:** Service requests that fail to connect or are aborted by either party will transition into a final, immutable terminal status code (e.g., `CANCELLED`, `TIMEOUT`, `REJECTED`). If a client wishes to retry, they cannot re-open the old record; the application must generate a brand-new `request_id` and duplicate necessary properties.
* **Consequences:**
    * **Pros:** Guarantees a pristine, reliable audit trail of customer behavior and mechanic response rates. Prevents accidental state bugs caused by reviving dead transactions.
    * **Cons:** Database table size grows faster since every single attempt results in a permanent row insert, requiring a long-term data archiving strategy.

---

## ADR 6: Polymorphic Client Entities
* **Status:** Approved
* **Context:** The platform must flexibly support two separate classifications of clients under a single authentication scheme: individual everyday drivers and corporate/B2B commercial transport operations or fleets.
* **Decision:** Implement a polymorphic relation mapping table named `entities.client`. It will map to a unified `entities.user` table for authentication credentials, but separate into individual (`entities.person`) or business (`entities.company`) tables for demographic tracking.
* **Consequences:**
    * **Pros:** Keeps core business tables like `vehicles` and `service_requests` tied cleanly to a single `client_id`, completely hiding whether that customer is a company or an individual during core transaction flows.
    * **Cons:** DBML syntax cannot natively express the data integrity constraint required here. The physical database migration script must hardcode an **Exclusive OR (XOR)** check constraint to guarantee data purity:
      ```sql
      ALTER TABLE entities.client ADD CONSTRAINT chk_client_entity_type 
      CHECK ((person_id IS NOT NULL AND company_id IS NULL) OR (company_id IS NOT NULL AND person_id IS NULL));
      ```

---

## ADR 7: Object-Relational Mapping (ORM) Layer via Spring Data JPA & Hibernate Spatial
* **Status:** Approved
* **Context:** The application needs a structured way to map relational database rows (Users, Vehicles, Service Requests) to Java objects. Because we are using PostGIS for location data, our database driver layer must natively understand how to convert PostGIS geographic byte streams into standard Java spatial objects without forcing us to manually parse Well-Known Text (WKT) or Well-Known Binary (WKB) strings in every repository query.
* **Decision:** We will use **Spring Data JPA (Hibernate)** as our core ORM framework, supplemented by the **`hibernate-spatial`** module.
* **Consequences:**
    * **Pros:** Standardizes CRUD operations using clean, abstract repository interfaces (`JpaRepository`).
    * **Pros:** `hibernate-spatial` automatically configures the database dialect to support geometric types. It bridges the gap between PostGIS and the industry-standard **JTS (Java Topology Suite)** library, allowing us to define coordinates in our Java entities using the native `org.locationtech.jts.geom.Point` class.
    * **Cons:** Introduces the performance and footprint overhead inherent to Hibernate (e.g., entity state tracking, risk of N+1 select query issues if relationships aren't fetched carefully).
    * **Cons:** Requires explicit alignment between the database coordinate system spatial reference identifiers (SRID 4326) and the geometry factories instantiated in Java helper utility code.