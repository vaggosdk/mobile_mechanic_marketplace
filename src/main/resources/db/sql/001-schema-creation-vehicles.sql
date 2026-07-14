--liquibase formatted sql
--changeset vstavrou:001-schema-creation-vehicles
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name = 'vehicles';
CREATE SCHEMA "vehicles";