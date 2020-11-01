DROP TABLE IF EXISTS quote;
 
CREATE TABLE quote (
  id INTEGER   PRIMARY KEY auto_increment,
  name VARCHAR(250) NOT NULL,
  price DOUBLE NOT NULL
);
 
DROP TABLE IF EXISTS item;
 
CREATE TABLE item (
  id INTEGER   PRIMARY KEY auto_increment,
  name VARCHAR(250) NOT NULL,
  quote_id INTEGER, 
  FOREIGN KEY (quote_id) REFERENCES quote(id)
);


INSERT INTO quote (id, name, price)
VALUES
  (1, 'bezeq', 100),
  (2, 'teva', 200),
  (3, 'delek', 300);  
  
  
INSERT INTO item (id, name, quote_id)
VALUES
  (1, 'shai',1),
  (2, 'shai',2),
  (3, 'shai',3),
  (4, 'golan',1),
    (5, 'dani',2);
  
  
DROP TABLE IF EXISTS quoteLog;
 
CREATE TABLE quoteLog (
  id INTEGER   PRIMARY KEY auto_increment,
  quote_id INTEGER   ,
  created_date TIMESTAMP NOT NULL,
  quote_operation VARCHAR(250) NOT NULL,
  error_code INTEGER ,
  message VARCHAR(250) NOT NULL,
);

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




--INSERT INTO users (id, username, email, password)
--VALUES
--  (1, 'shai', 'shai@gmail.com' , '111111'),
--  (2, 'guy', 'shai@gmail.com' , '222222');
  
  
INSERT INTO roles (id, name)
VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_MODERATOR'),
  (3, 'ROLE_ADMIN');
  
--  insert into user_roles(user_id,role_id) values
--  (1,1),
--  (1,2),
--  (1,3),
--  (2,1),
--  (2,2),
--  (2,3);
  

