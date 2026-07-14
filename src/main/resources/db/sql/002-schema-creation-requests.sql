--liquibase formatted sql
--changeset vstavrou:002-schema-creation-requests
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name = 'requests';
CREATE SCHEMA "requests";