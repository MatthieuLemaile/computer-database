FROM mysql:5.5

ENV MYSQL_ROOT_PASSWORD=toor
EXPOSE 3306

ADD 1-SCHEMA.sql /docker-entrypoint-initdb.d/1-SCHEMA.sql
ADD 2-PRIVILEGES.sql /docker-entrypoint-initdb.d/2-PRIVILEGES.sql
ADD 3-ENTRIES.sql /docker-entrypoint-initdb.d/3-ENTRIES.sql

#RUN /bin/bash -c "/usr/bin/mysqld_safe &" && \
#  sleep 5 && \
#  mysql -u root < /tmp/1-SCHEMA.sql && \
#  mysql -u root computer-database-db < /tmp/2-PRIVILEGES.sql && \
#  mysql -u root computer-database-db < /tmp/3-ENTRIES.sql

#docker run -d --name=mysql --net=prod-network --ip=172.18.0.2 mysql
