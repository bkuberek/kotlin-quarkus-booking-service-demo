CREATE TABLE restaurant
(
    id           UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
    name         VARCHAR(255)             NOT NULL,
    created_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP WITH TIME ZONE          DEFAULT NULL,
    CONSTRAINT restaurant_name_unq UNIQUE (name)
);
CREATE INDEX restaurant_created_time_idx ON restaurant (created_time);
CREATE INDEX restaurant_updated_time_idx ON restaurant (updated_time NULLS LAST);


CREATE TABLE restaurant_endorsements
(
    restaurant_id UUID        NOT NULL,
    endorsement   Endorsement NOT NULL,
    PRIMARY KEY (restaurant_id, endorsement),
    CONSTRAINT restaurant_endorsements_restaurant_id_fk FOREIGN KEY (restaurant_id) REFERENCES restaurant
);


CREATE TABLE restaurant_tables
(
    restaurant_id UUID    NOT NULL,
    size          INTEGER NOT NULL,
    quantity      INTEGER NOT NULL,
    PRIMARY KEY (restaurant_id, size),
    CONSTRAINT restaurant_endorsements_restaurant_id_fk FOREIGN KEY (restaurant_id) REFERENCES restaurant
);

