  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  CREATE USER 'admincdb'@'172.18.0.3' IDENTIFIED BY 'qwerty1234';

  GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'admincdb'@'172.18.0.3' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
