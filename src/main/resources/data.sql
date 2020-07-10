-- create persistent_logins for remember me feature, keep the token and time to live for token
create table if not exists persistent_logins (
  username varchar_ignorecase(100) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);

-- Insert User

-- password : 123456
INSERT into USER (user_id, first_name, last_name, email, role, username, password, active, points)
        values (1,'John', 'smith', 'john@gmail.com', 'ADMIN', 'admin', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 0);

-- password : 123456
INSERT into USER (user_id, first_name, last_name, email, role, username, password, active, points)
        values (2,'Anna', 'smith', 'ana@gmail.com', 'SELLER', 'seller',
        '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 0);

-- password : 123456
INSERT into USER (user_id, first_name, last_name, email, role, username, password, active, points)
        values (3,'Anthony', 'copy', 'john@gmail.com', 'BUYER', 'buyer',
        '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 0);