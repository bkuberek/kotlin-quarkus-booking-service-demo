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
