  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  CREATE USER 'admincdb'@'172.19.0.3' IDENTIFIED BY 'qwerty1234';

  GRANT ALL PRIVILEGES ON `computer-database-db-test`.* TO 'admincdb'@'172.19.0.3' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
