DROP TABLE IF EXISTS users;
 
CREATE TABLE  users(
  id INTEGER   PRIMARY KEY auto_increment,
  username VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  password  VARCHAR(250) NOT NULL
);


DROP TABLE IF EXISTS roles;
 
CREATE TABLE roles (
  id INTEGER   PRIMARY KEY auto_increment,
  name VARCHAR(250) NOT NULL
);


DROP TABLE IF EXISTS user_roles;
 
CREATE TABLE user_roles (
  user_id  INTEGER NOT NULL,
  role_id INTEGER NOT NULL
);



 
  
INSERT INTO roles (id, name)
VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_MODERATOR'),
  (3, 'ROLE_ADMIN');
  

