--liquibase formatted sql
--changeset vstavrou:013-create-request-status-def-tbl
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'requests' AND table_name = 'request_status_def';
CREATE TABLE "requests"."request_status_def"
(
    "request_status_id"   bigint PRIMARY KEY,
    "request_status_name" varchar(255) NOT NULL
);

COMMENT
ON TABLE "requests"."request_status_def" IS 'This is the table that will act as lookup table for the request statuses.' ;

COMMENT
ON COLUMN "requests"."request_status_def"."request_status_name" IS 'Pending, Accepted, Completed, Cancelled, Expired';
