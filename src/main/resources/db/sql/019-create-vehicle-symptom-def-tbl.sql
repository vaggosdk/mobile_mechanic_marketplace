--liquibase formatted sql
--changeset vstavrou:019-create-vehicle-symptom-def-tbl
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'requests' AND table_name = 'vehicle_symptom_def';
CREATE TABLE "requests"."vehicle_symptom_def"
(
    "symptom_code" varchar(255) PRIMARY KEY,
    "symptom_desc" varchar(255)
);


COMMENT
ON TABLE "requests"."request_status_def" IS 'This is the table that will act as lookup table for several vehicle symptoms. There will also be a code OTHER, so users can choose other that existing codes.' ;
