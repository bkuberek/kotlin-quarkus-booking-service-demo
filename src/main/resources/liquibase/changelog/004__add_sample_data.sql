INSERT INTO restaurant (id, name, created_time)
VALUES ('635dc3bd-c515-4d41-848b-bc487bb13810', 'Lardo', '2017-03-24T00:00:00.00Z'),
       ('dfe2cab1-6a39-4426-8937-c1d89403e0f0', 'Panadería Rosetta', '2007-8-15T00:00:00.00Z'),
       ('b1e6728c-da7c-4841-bbf3-ba7e97f7e07c', 'Tetetlán', '2003-11-01T00:00:00.00Z'),
       ('c52e1d11-757a-48dc-88e8-4bf9866ca53a', 'Falling Piano Brewing Co', '2020-05-14T00:00:00.00Z'),
       ('d42c8608-7d52-4ea3-823f-c59b68a33407', 'u.to.pi.a', '2022-09-17T00:00:00.00Z');

INSERT INTO restaurant_endorsement (restaurant_id, endorsement)
VALUES ('635dc3bd-c515-4d41-848b-bc487bb13810', 'gluten'),
       ('dfe2cab1-6a39-4426-8937-c1d89403e0f0', 'gluten'),
       ('dfe2cab1-6a39-4426-8937-c1d89403e0f0', 'vegetarian'),
       ('b1e6728c-da7c-4841-bbf3-ba7e97f7e07c', 'gluten'),
       ('b1e6728c-da7c-4841-bbf3-ba7e97f7e07c', 'paleo'),
       ('d42c8608-7d52-4ea3-823f-c59b68a33407', 'vegan'),
       ('d42c8608-7d52-4ea3-823f-c59b68a33407', 'vegetarian');

INSERT INTO restaurant_table (restaurant_id, size, quantity)
VALUES ('635dc3bd-c515-4d41-848b-bc487bb13810', 2, 4),
       ('635dc3bd-c515-4d41-848b-bc487bb13810', 4, 2),
       ('635dc3bd-c515-4d41-848b-bc487bb13810', 6, 1),
       ('dfe2cab1-6a39-4426-8937-c1d89403e0f0', 2, 3),
       ('dfe2cab1-6a39-4426-8937-c1d89403e0f0', 4, 2),
       ('dfe2cab1-6a39-4426-8937-c1d89403e0f0', 6, 0),
       ('b1e6728c-da7c-4841-bbf3-ba7e97f7e07c', 2, 2),
       ('b1e6728c-da7c-4841-bbf3-ba7e97f7e07c', 4, 4),
       ('b1e6728c-da7c-4841-bbf3-ba7e97f7e07c', 6, 1),
       ('c52e1d11-757a-48dc-88e8-4bf9866ca53a', 2, 5),
       ('c52e1d11-757a-48dc-88e8-4bf9866ca53a', 4, 5),
       ('c52e1d11-757a-48dc-88e8-4bf9866ca53a', 6, 5),
       ('d42c8608-7d52-4ea3-823f-c59b68a33407', 2, 2),
       ('d42c8608-7d52-4ea3-823f-c59b68a33407', 4, 0),
       ('d42c8608-7d52-4ea3-823f-c59b68a33407', 6, 0);

INSERT INTO reservation (id, restaurant_id, name, size, is_active, restrictions, reservation_time, created_time,
                         updated_time)
VALUES ('0fda5960-741c-4ad9-8bc5-b0e86391332e', 'd42c8608-7d52-4ea3-823f-c59b68a33407', 'Michael', 2, true,
        'vegetarian', (CURRENT_DATE + INTERVAL '1 day' + TIME '16:00:00'), (CURRENT_TIMESTAMP - INTERVAL '1 day'),
        (CURRENT_TIMESTAMP - INTERVAL '1 hour')),
       ('01a43d43-978a-43c4-8d9a-4849b71e6277', 'd42c8608-7d52-4ea3-823f-c59b68a33407', 'George Michael', 2, true,
        'vegetarian,gluten', (CURRENT_DATE + INTERVAL '1 day' + TIME '16:00:00'),
        (CURRENT_TIMESTAMP - INTERVAL '12 hour'), null),
       ('d74c9f7e-c9b3-4f98-8ff3-b9cfb8122d37', '635dc3bd-c515-4d41-848b-bc487bb13810', 'Lucile', 5, true, 'gluten',
        (CURRENT_DATE + INTERVAL '1 day' + TIME '16:00:00'), (CURRENT_TIMESTAMP - INTERVAL '3 hour'), null),
       ('3ca1ddc3-1ba1-4dfb-9a32-a33259b7fe0c', 'b1e6728c-da7c-4841-bbf3-ba7e97f7e07c', 'Gob', 2, true, 'paleo',
        (CURRENT_DATE + INTERVAL '1 day' + TIME '16:00:00'), (CURRENT_TIMESTAMP - INTERVAL '1 week'), null),
       ('ae5a8791-43dc-4fee-a6c5-5d6be12344ed', 'c52e1d11-757a-48dc-88e8-4bf9866ca53a', 'Tobias', 7, true, null,
        (CURRENT_DATE + INTERVAL '1 day' + TIME '16:00:00'), (CURRENT_TIMESTAMP - INTERVAL '36 hour'), null),
       ('0f69765d-4556-4f21-9925-fc1314a206e3', 'dfe2cab1-6a39-4426-8937-c1d89403e0f0', 'Maeby', 3, true, 'vegan',
        (CURRENT_DATE + INTERVAL '1 day' + TIME '17:00:00'), (CURRENT_TIMESTAMP - INTERVAL '22 hour'), null),
       ('da79135a-8358-41c2-904f-6f6b70104939', '635dc3bd-c515-4d41-848b-bc487bb13810', 'Daphinetly', 4, true, null,
        (CURRENT_DATE + INTERVAL '1 day' + TIME '18:00:00'), (CURRENT_TIMESTAMP - INTERVAL '15 minutes'), null);

INSERT INTO reservation_table (reservation_id, size, quantity)
VALUES ('0fda5960-741c-4ad9-8bc5-b0e86391332e', 2, 1),
       ('01a43d43-978a-43c4-8d9a-4849b71e6277', 2, 1),
       ('d74c9f7e-c9b3-4f98-8ff3-b9cfb8122d37', 6, 1),
       ('3ca1ddc3-1ba1-4dfb-9a32-a33259b7fe0c', 2, 1),
       -- Tobias needs 2 tables for a party of 7
       ('ae5a8791-43dc-4fee-a6c5-5d6be12344ed', 6, 1),
       ('ae5a8791-43dc-4fee-a6c5-5d6be12344ed', 2, 1),
       ('0f69765d-4556-4f21-9925-fc1314a206e3', 4, 1),
       ('da79135a-8358-41c2-904f-6f6b70104939', 4, 1);
