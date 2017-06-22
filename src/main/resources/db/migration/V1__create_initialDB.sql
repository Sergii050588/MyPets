
CREATE TABLE pets (
  pet_id bigserial PRIMARY KEY  -- implicit primary key constraint
, pet_name  varchar(255)
, pet_type  integer
);

CREATE TABLE users (
  user_id  bigserial PRIMARY KEY
, user_name   varchar(255)
);
