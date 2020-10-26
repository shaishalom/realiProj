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

