UPDATE users
SET password = '$2a$10$hwqCoxQAWfPZvNcPzW2Y5.JQRzeEPPHspA/JFMED72bi6f1ACAL2O'
WHERE username = 'admin'
  AND password = '$2a$10$wY1yM8aWqB4y3ZkM3tI/oefjUqYtqA3Z1w5Z/gI9K8q/t4J5bK5S.';

ALTER TABLE population_records
    ADD CONSTRAINT uk_population_village_year UNIQUE (village_id, reference_year);

ALTER TABLE villages MODIFY created_at DATETIME;
ALTER TABLE villages MODIFY last_modified_at DATETIME;

ALTER TABLE village_names MODIFY created_at DATETIME;
ALTER TABLE village_names MODIFY last_modified_at DATETIME;

ALTER TABLE population_records MODIFY created_at DATETIME;
ALTER TABLE population_records MODIFY last_modified_at DATETIME;
