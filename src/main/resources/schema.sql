CREATE TABLE webhook_data (
    id VARCHAR(255) NOT NULL,
    type VARCHAR(255),
    asset VARCHAR(255),
    cents VARCHAR(255),
    status VARCHAR(255),
    updated_at BIGINT,
    PRIMARY KEY (id)
);
