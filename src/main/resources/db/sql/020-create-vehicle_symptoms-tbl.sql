--liquibase formatted sql
--changeset vstavrou:020-create-vehicle_symptoms-tbl
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'requests' AND table_name = 'request_vehicle_symptom';
CREATE TABLE "requests"."request_vehicle_symptom"
(
    "symptom_code"               varchar(255),
    "request_id"                 bigint NOT NULL,
    "custom_symptom_description" varchar(255),
    PRIMARY KEY ("symptom_code", "request_id")
);

COMMENT
ON TABLE "requests"."request_vehicle_symptom" IS 'Table to hold the vehicle symptoms of a request.'
COMMENT
ON COLUMN "requests"."request_vehicle_symptom"."custom_symptom_description" IS 'Used when symptom_code is OTHER, or for extra request-specific details';
