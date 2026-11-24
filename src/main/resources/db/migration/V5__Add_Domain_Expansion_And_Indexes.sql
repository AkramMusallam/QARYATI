CREATE TABLE historical_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    village_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    event_year INT,
    period VARCHAR(100),
    description TEXT,
    source_reference VARCHAR(500),
    created_by VARCHAR(100),
    created_at DATETIME,
    last_modified_by VARCHAR(100),
    last_modified_at DATETIME,
    CONSTRAINT fk_historical_events_village_id FOREIGN KEY (village_id) REFERENCES villages(id) ON DELETE CASCADE
);

CREATE TABLE landmarks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    village_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    latitude DOUBLE,
    longitude DOUBLE,
    source_reference VARCHAR(500),
    created_by VARCHAR(100),
    created_at DATETIME,
    last_modified_by VARCHAR(100),
    last_modified_at DATETIME,
    CONSTRAINT fk_landmarks_village_id FOREIGN KEY (village_id) REFERENCES villages(id) ON DELETE CASCADE
);

CREATE TABLE economy_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    village_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    created_by VARCHAR(100),
    created_at DATETIME,
    last_modified_by VARCHAR(100),
    last_modified_at DATETIME,
    CONSTRAINT fk_economy_items_village_id FOREIGN KEY (village_id) REFERENCES villages(id) ON DELETE CASCADE
);

CREATE TABLE public_facilities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    village_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    facility_type VARCHAR(100) NOT NULL,
    description TEXT,
    created_by VARCHAR(100),
    created_at DATETIME,
    last_modified_by VARCHAR(100),
    last_modified_at DATETIME,
    CONSTRAINT fk_public_facilities_village_id FOREIGN KEY (village_id) REFERENCES villages(id) ON DELETE CASCADE
);

CREATE INDEX idx_villages_governorate ON villages(governorate);
CREATE INDEX idx_villages_current_name_ar ON villages(current_name_ar);
CREATE INDEX idx_villages_current_name_en ON villages(current_name_en);
CREATE INDEX idx_village_names_village_id ON village_names(village_id);
CREATE INDEX idx_population_village_year ON population_records(village_id, reference_year);
CREATE INDEX idx_historical_events_village_id ON historical_events(village_id);
CREATE INDEX idx_landmarks_village_id ON landmarks(village_id);
CREATE INDEX idx_economy_items_village_id ON economy_items(village_id);
CREATE INDEX idx_public_facilities_village_id ON public_facilities(village_id);
