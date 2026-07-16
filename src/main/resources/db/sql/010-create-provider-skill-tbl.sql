--liquibase formatted sql
--changeset vstavrou:010-create-provider-skill-tbl
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'entities' AND table_name = 'provider_skill';
CREATE TABLE "entities"."provider_skill"
(
    "provider_id" bigint,
    "skill_id"    integer,
    PRIMARY KEY ("provider_id", "skill_id")
);

COMMENT
ON TABLE "entities"."provider_skill" IS 'This is the table that will hold the service providers'' skills using the look up table.' ;