CREATE TABLE reservation
(
    id               UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
    restaurant_id    UUID                     NOT NULL,
    name             TEXT                     NOT NULL,
    size             INTEGER                  NOT NULL,
    restrictions     TEXT                     NULL DEFAULT NULL,
    is_active        BOOLEAN                  NOT NULL DEFAULT TRUE,
    reservation_time TIMESTAMP WITH TIME ZONE NOT NULL,
    created_time     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time     TIMESTAMP WITH TIME ZONE          DEFAULT NULL,
    CONSTRAINT reservation_restaurant_id_fk FOREIGN KEY (restaurant_id) REFERENCES restaurant
);
CREATE INDEX reservation_is_active_idx ON reservation (is_active);
CREATE INDEX reservation_reservation_time_idx ON reservation (reservation_time);
CREATE INDEX reservation_created_time_idx ON reservation (created_time);
CREATE INDEX reservation_updated_time_idx ON reservation (updated_time NULLS LAST);
