--liquibase formatted sql
--changeset vstavrou:002a-enable-postgis
CREATE EXTENSION IF NOT EXISTS postgis;
