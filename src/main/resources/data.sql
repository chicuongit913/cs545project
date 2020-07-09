-- create persistent_logins for remember me feature, keep the token and time to live for token
create table if not exists persistent_logins (
  username varchar_ignorecase(100) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);

-- Insert role for the role user
INSERT INTO ROLE(role_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLE(role_id, role) VALUES (2, 'ROLE_SELLER');
INSERT INTO ROLE(role_id, role) VALUES (3, 'ROLE_BUYER');

-- Insert User

-- password : 123456
INSERT into USER (user_id, first_name, last_name, email, role_id, username, password, active)
        values (1,'John', 'smith', 'john@gmail.com', 1, 'admin', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1);

-- password : 123456
INSERT into USER (user_id, first_name, last_name, email, role_id, username, password, active)
        values (2,'Anna', 'smith', 'ana@gmail.com', 2, 'seller', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1);

-- password : 123456
INSERT into USER (user_id, first_name, last_name, email, role_id, username, password, active)
        values (3,'Anthony', 'copy', 'john@gmail.com', 3, 'buyer', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1);