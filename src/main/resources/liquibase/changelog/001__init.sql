CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop type if exists Endorsement cascade;
create type Endorsement as enum (
    'gluten',
    'paleo',
    'vegan',
    'vegetarian');

create cast (varchar as Endorsement) with inout as implicit;
create cast (Endorsement as varchar) with inout as implicit;

