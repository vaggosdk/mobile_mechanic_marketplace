--liquibase formatted sql
--changeset vstavrou:022-foreign-keys-batch-addition
ALTER TABLE "entities"."person"
    ADD FOREIGN KEY ("user_id") REFERENCES "entities"."user" ("user_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "entities"."company"
    ADD FOREIGN KEY ("contact_person_id") REFERENCES "entities"."person" ("person_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "entities"."company"
    ADD FOREIGN KEY ("user_id") REFERENCES "entities"."user" ("user_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "entities"."client"
    ADD FOREIGN KEY ("person_id") REFERENCES "entities"."person" ("person_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "entities"."client"
    ADD FOREIGN KEY ("company_id") REFERENCES "entities"."company" ("company_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "vehicles"."user_vehicles"
    ADD FOREIGN KEY ("client_id") REFERENCES "entities"."client" ("client_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "vehicles"."user_vehicles"
    ADD FOREIGN KEY ("catalog_id") REFERENCES "vehicles"."vehicle_catalog" ("catalog_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "entities"."service_provider"
    ADD FOREIGN KEY ("person_id") REFERENCES "entities"."person" ("person_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "entities"."service_provider"
    ADD FOREIGN KEY ("company_id") REFERENCES "entities"."company" ("company_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "entities"."service_provider"
    ADD FOREIGN KEY ("profession_id") REFERENCES "entities"."profession" ("profession_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "entities"."provider_skill"
    ADD FOREIGN KEY ("provider_id") REFERENCES "entities"."service_provider" ("provider_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "entities"."provider_skill"
    ADD FOREIGN KEY ("skill_id") REFERENCES "entities"."skill" ("skill_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."service_requests"
    ADD FOREIGN KEY ("client_id") REFERENCES "entities"."client" ("client_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."service_requests"
    ADD FOREIGN KEY ("vehicle_id") REFERENCES "vehicles"."user_vehicles" ("vehicle_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."service_requests"
    ADD FOREIGN KEY ("provider_id") REFERENCES "entities"."service_provider" ("provider_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."service_requests"
    ADD FOREIGN KEY ("request_status_id") REFERENCES "requests"."request_status_def" ("request_status_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."review"
    ADD FOREIGN KEY ("request_id") REFERENCES "requests"."service_requests" ("request_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."review"
    ADD FOREIGN KEY ("review_scale_id") REFERENCES "requests"."review_scale_def" ("review_scale_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."request_attachments"
    ADD FOREIGN KEY ("request_id") REFERENCES "requests"."service_requests" ("request_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."request_obd_codes"
    ADD FOREIGN KEY ("request_id") REFERENCES "requests"."service_requests" ("request_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."request_vehicle_symptom"
    ADD FOREIGN KEY ("symptom_code") REFERENCES "requests"."vehicle_symptom_def" ("symptom_code") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."request_vehicle_symptom"
    ADD FOREIGN KEY ("request_id") REFERENCES "requests"."service_requests" ("request_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "requests"."quote"
    ADD FOREIGN KEY ("request_id") REFERENCES "requests"."service_requests" ("request_id") DEFERRABLE INITIALLY IMMEDIATE;
