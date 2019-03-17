-- db test 
CREATE TABLE t_user (
  id varchar(30) NOT NULL,
  user_name varchar(60) NOT NULL,
  PRIMARY KEY (id)
);

-- db logdb
CREATE TABLE t_log (
  id varchar(32) DEFAULT NULL,
  log varchar(20) DEFAULT NULL
);