# Mobile Mechanic Marketplace - Project Vision

## Elevator Pitch

A platform inspired by Uber/Wolt principles that connects vehicle owners with verified mechanics for roadside
assistance, diagnostics, maintenance, and workshop services.

Customers can request a service on demand, while mechanics can accept or reject requests based on expertise,
availability, and service area.

The primary goals are:

- Convenience
- Transparency
- Trust
- Safety

---

# Core Business Concept

A customer submits a service request.

The platform notifies matching mechanics based on:

- Service area
- Availability
- Vehicle type
- Brand expertise
- Requested service

A mechanic may:

- Accept
- Reject

The customer receives status updates.

After diagnosis, the mechanic may:

- Resolve the issue on-site
- Provide an estimate
- Recommend vehicle transfer to their workshop

---

# User Types

## Customer

Can:

- Register multiple vehicles
- Register vehicles permanently
- Register vehicles ad-hoc for one-time use
- Request services for another person's vehicle
- Upload photos/videos before mechanic arrival
- Share diagnostic information
- View mechanic profile
- View ratings and reviews
- Receive quotes/estimates
- Track request status
- Access Safety Center

## Mechanic

Can:

- Register profile
- Verify professional license
- Define service area
- Define availability
- Enable ad-hoc availability
- Define brand expertise
- Define special skills
- Accept/reject jobs
- Submit diagnosis reports
- Create quotes/estimates
- Request workshop transfer

---

# Trust & Safety

## Mechanic Verification

Mechanics must provide:

- Professional license
- Identity verification
- Contact information

Only verified mechanics can receive jobs.

## Safety Center

Customer can:

- Report issue
- Contact support
- Trigger emergency assistance flow

---

# Vehicle Management

A customer can store:

- Cars
- Motorcycles

Vehicle information:

- Brand
- Model
- Year
- Registration number
- Engine type
- Notes

---

# OBD & Diagnostic Data

The platform should support sharing vehicle diagnostic information with mechanics before arrival.

## MVP Approach

Customers may upload:

- Dashboard warning light photos
- OBD application screenshots
- Diagnostic reports (PDF/Image)
- Additional notes regarding symptoms

## Vehicle Symptoms

Customers may optionally select:

- Check Engine Light
- Vehicle Won't Start
- Strange Noise
- Battery Issue
- Flat Tire
- Oil Leak
- Excessive Smoke
- Overheating
- Brake Problem
- Other

## Structured Diagnostic Data (Future)

Customers may manually provide fault codes.

Example:

- P0420
- P0301

## OBD Application Integration (Future)

Possible integrations:

- Car Scanner
- Torque
- OBD Auto Doctor

## Native OBD Scanner Integration (Long-Term Vision)

Potential capabilities:

- Automatic fault code retrieval
- Live sensor data
- Vehicle health reports

---

# Service Categories

## Roadside

- Diagnostics
- Battery issues
- Jump start
- Flat tire assistance
- Emergency troubleshooting

## Workshop

- Scheduled maintenance
- Repairs
- Brake replacement
- Clutch replacement
- Engine work

## Detailing

- At customer location
- At mechanic/detailer location

---

# Mechanic Matching Logic

Matching criteria:

- Service Area
- Vehicle Type (Car / Motorcycle)
- Brand Expertise
- Service Category
- Current Availability
- Rating
- Distance from Customer

---

# Request Flow

1. Customer selects vehicle
2. Customer selects service category
3. Customer uploads photos/videos
4. Customer optionally uploads diagnostic information
5. Matching mechanics receive request
6. Mechanic accepts/rejects
7. Customer receives status update
8. Mechanic performs diagnosis
9. Mechanic submits estimate
10. Customer accepts/rejects estimate
11. Service completed

---

# Features Included In Initial Product Vision

## Customer Features

- Vehicle profiles
- Ad-hoc vehicle registration
- Request service for another person
- Photo upload
- Video upload
- Diagnostic data sharing
- Mechanic ratings
- Reviews
- Quote approval

## Mechanic Features

- License verification
- Brand expertise
- Skills
- Service area
- Availability schedule
- Ad-hoc availability
- Request management
- Estimate creation

---

# Features Explicitly Deferred

- Payments
- Real-time GPS tracking
- Chat system
- Subscription plans
- Insurance integrations
- Fleet management
- AI diagnostics
- Native OBD scanner integration
- Live vehicle telemetry

---

# Open Questions

## Business Model

Potential options:

1. Commission per completed job
2. Monthly subscription for mechanics
3. Featured mechanic listings
4. Lead generation model

## Legal / Insurance

Need research regarding:

- Vehicle transport liability
- Mechanic insurance requirements
- Platform liability limitations

---

# Current Working Assumption

Build backend-first MVP.

Goals:

1. Learn backend engineering
2. Validate real user interest
3. Potentially evolve into a real product

Success criteria:

- Real mechanics registered
- Real requests submitted
- Feedback collected from both sides
