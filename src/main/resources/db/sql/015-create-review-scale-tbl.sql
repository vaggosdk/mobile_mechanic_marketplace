--liquibase formatted sql
--changeset vstavrou:015-create-review-scale-tbl
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'requests' AND table_name = 'review_scale_def';
CREATE TABLE "requests"."review_scale_def"
(
    "review_scale_id" integer PRIMARY KEY,
    "description"     varchar(255)
);

COMMENT
ON TABLE "entities"."user" IS 'This is the table that will act as lookup table for the review scale' ;