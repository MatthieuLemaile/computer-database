FROM mysql:5.5

ENV MYSQL_ROOT_PASSWORD=toor
EXPOSE 3306

COPY 1-SCHEMA.sql /docker-entrypoint-initdb.d/1-SCHEMA.sql
COPY 2-PRIVILEGES.sql /docker-entrypoint-initdb.d/2-PRIVILEGES.sql

#RUN /bin/bash -c "/usr/bin/mysqld_safe &" && \
#  sleep 5 && \
#  mysql -u root < /tmp/1-SCHEMA.sql && \
#  mysql -u root computer-database-db < /tmp/2-PRIVILEGES.sql && \
#  mysql -u root computer-database-db < /tmp/3-ENTRIES.sql

#docker run -d --name=mysql-test --net=test-network --ip=172.19.0.2 mysql-test
