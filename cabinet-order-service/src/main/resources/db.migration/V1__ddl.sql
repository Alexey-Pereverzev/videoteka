CREATE TABLE orders (
                           id bigserial NOT NULL PRIMARY KEY,
                           user_id int8 NOT NULL,
                           film_id int8 NOT NULL,
                           price int4 NOT NULL,
                           type varchar NOT NULL,
                           created_by varchar NULL,
                           created_when timestamp NULL,
                           update_by varchar NULL,
                           update_when timestamp NULL,
                           is_deleted bool NULL DEFAULT false,
                           deleted_by varchar NULL,
                           deleted_when timestamp NULL,
                           rent_start timestamp NULL,
                           rent_end timestamp NULL
);


--INSERT INTO orders (user_id,film_id,price,type, created_by,created_when,update_by,update_when,is_deleted,deleted_by,deleted_when,rent_start,rent_end) VALUES
--                           (1,1,100,"Маленькие секреты",NULL,NULL,NULL,NULL,false,NULL,NULL,Null,Null);


