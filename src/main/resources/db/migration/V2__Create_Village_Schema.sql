CREATE TABLE villages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_name_ar VARCHAR(255) NOT NULL,
    current_name_en VARCHAR(255),
    meaning_or_origin TEXT,
    governorate VARCHAR(255) NOT NULL,
    administrative_affiliation VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    location_description TEXT,
    elevation INT,
    land_area DOUBLE,
    nearby_locations TEXT,
    historical_overview TEXT,
    cultural_characteristics TEXT,

    created_by VARCHAR(100),
    created_at TIMESTAMP,
    last_modified_by VARCHAR(100),
    last_modified_at TIMESTAMP
);

CREATE TABLE village_names (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    village_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    language VARCHAR(50),
    name_type VARCHAR(50),
    historical_context TEXT,

    created_by VARCHAR(100),
    created_at TIMESTAMP,
    last_modified_by VARCHAR(100),
    last_modified_at TIMESTAMP,

    CONSTRAINT fk_village_names_village_id FOREIGN KEY (village_id) REFERENCES villages(id) ON DELETE CASCADE
);

CREATE TABLE population_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    village_id BIGINT NOT NULL,
    population_value INT NOT NULL,
    reference_year INT NOT NULL,
    source_information VARCHAR(500) NOT NULL,

    created_by VARCHAR(100),
    created_at TIMESTAMP,
    last_modified_by VARCHAR(100),
    last_modified_at TIMESTAMP,

    CONSTRAINT fk_population_village_id FOREIGN KEY (village_id) REFERENCES villages(id) ON DELETE CASCADE
);
