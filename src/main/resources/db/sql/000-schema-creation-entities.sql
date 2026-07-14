--liquibase formatted sql
--changeset vstavrou:000-schema-creation-entities
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name = 'entities';
CREATE SCHEMA "entities";