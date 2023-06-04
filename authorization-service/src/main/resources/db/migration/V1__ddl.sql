CREATE TABLE roles (
                       id bigserial NOT NULL PRIMARY KEY,
                       title varchar NOT NULL
);

CREATE TABLE users (
                       id bigserial NOT NULL PRIMARY KEY,
                       username varchar NOT NULL,
                       password varchar NOT NULL,
                       first_name varchar NOT NULL,
                       last_name varchar NOT NULL,
                       email varchar NOT NULL,
                       phone varchar NULL,
                       address varchar NULL,
                       role_id int8 NOT NULL,
                       created_by varchar NULL,
                       created_when timestamp NULL,
                       update_by varchar NULL,
                       update_when timestamp NULL,
                       is_deleted bool NULL DEFAULT false,
                       deleted_by varchar NULL,
                       deleted_when timestamp NULL,
                       CONSTRAINT users_fk FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO roles (title) VALUES
                              ('ROLE_ADMIN'),
                              ('ROLE_USER'),
                              ('ROLE_MANAGER');

INSERT INTO users (username, password, first_name,
                   last_name, email, phone, address, role_id,
                   created_by, created_when, update_by, update_when,
                   is_deleted, deleted_by, deleted_when) VALUES
             ('admin', '$2a$10$2/ADJRmO6PwcURKpH0in7.9wmayZdrktHb/NvyJEQv4Nmy9QoIyr6',
              'Default', 'Admin', 'admin@mail.ru', NULL, NULL, 1,
              NULL, NULL, NULL, NULL, false, NULL, NULL),
             ('customer', '$2a$10$QuakOKczPKpUZQm63AK7cuxXEn4pMDsYL4BgG0UaTckFb0duLVsqq',
              'Default', 'Customer', 'customer@mail.ru', NULL, NULL, 2,
              NULL, NULL, NULL, NULL, false, NULL, NULL),
             ('manager', '$2a$10$oVCrirqnU63KMRhF4EFcEutoQ7CfmhR68pDLi62qok1TJ1q525bOC',
              'Default', 'Manager', 'manager@mail.ru', NULL, NULL, 3,
              NULL, NULL, NULL, NULL, false, NULL, NULL);