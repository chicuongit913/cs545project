-- create persistent_logins for remember me feature, keep the token and time to live for token
create table if not exists persistent_logins (
  username varchar_ignorecase(100) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);

-- Insert role for the role user
-- INSERT INTO ROLE(role_id, role) VALUES (1, 'ROLE_ADMIN');
-- INSERT INTO ROLE(role_id, role) VALUES (2, 'ROLE_SELLER');
-- INSERT INTO ROLE(role_id, role) VALUES (3, 'ROLE_BUYER');

-- Insert User

-- password : 123456
INSERT into USER (user_id, first_name, last_name, email, role, username, password, active, points)
        values (1,'John', 'smith', 'john@gmail.com', 'ROLE_ADMIN', 'admin', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 0);

-- password : 123456
INSERT into USER (user_id, first_name, last_name, email, role, username, password, active, points)
        values (2,'Anna', 'smith', 'ana@gmail.com', 'ROLE_SELLER', 'seller',
        '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 0);

-- password : 123456
INSERT into USER (user_id, first_name, last_name, email, role, username, password, active, points)
        values (3,'Anthony', 'copy', 'john@gmail.com', 'ROLE_BUYER', 'buyer',
        '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 0);