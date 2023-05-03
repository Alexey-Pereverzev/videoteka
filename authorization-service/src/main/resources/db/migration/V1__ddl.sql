

CREATE TABLE roles (
                           id bigserial NOT NULL PRIMARY KEY,
                           title varchar NOT NULL
);

CREATE TABLE users (
                           id bigserial NOT NULL PRIMARY KEY,
                           username varchar NOT NULL,
                           password varchar NOT NULL,
                           first_name varchar NULL,
                           last_name varchar NULL,
                           email varchar NOT NULL,
                           phone varchar NULL,
                           address varchar NULL,
                           created_by varchar NULL,
                           created_when timestamp NULL,
                           update_by varchar NULL,
                           update_when timestamp NULL,
                           is_deleted bool NULL DEFAULT false,
                           deleted_by varchar NULL,
                           deleted_when timestamp NULL
);

CREATE TABLE user_roles (
                                 user_id int8 NOT NULL,
                                 role_id int8 NOT NULL,
                                 CONSTRAINT user_roles_pk PRIMARY KEY (user_id, role_id),
                                 CONSTRAINT user_roles_fk FOREIGN KEY (role_id) REFERENCES public.roles(id),
                                 CONSTRAINT user_roles_fk_1 FOREIGN KEY (user_id) REFERENCES public.users(id)
);

INSERT INTO roles (title) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER'),
    ('ROLE_MANAGER');

INSERT INTO users (username, password, first_name,
                   last_name, email, phone, address,
                   created_by, created_when, update_by, update_when,
                   is_deleted, deleted_by, deleted_when) VALUES
        ('admin', '$2a$10$cY.b0rBqonPVXKRSx713Sew7VJ2li8Qj10pgL5PpeXukgKQ7NIWvO',
         'Default', 'Admin', 'admin@mail.ru', NULL, NULL,
         NULL, NULL, NULL, NULL, false, NULL, NULL),
        ('customer', '$2a$10$h7mKPyxAFtmJv3cW8sQHxe8R1CLjA0x.CUTqn.u/yUyL8z1QVqN7q',
         'Default', 'Customer', 'customer@mail.ru', NULL, NULL,
         NULL, NULL, NULL, NULL, false, NULL, NULL),
        ('manager', '$2a$10$vFlKg.dO1VYJ7NQkEC7Dse3YNMAPWuqW8rp3rQNrR2cXrAP.bSYQm',
         'Default', 'Manager', 'manager@mail.ru', NULL, NULL,
         NULL, NULL, NULL, NULL, false, NULL, NULL);

INSERT INTO user_roles (user_id,role_id) VALUES
          (1,1), (2,2), (3,3);