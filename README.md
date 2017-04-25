Training: computer-database
===========================

https://github.com/excilys/training-java

# Content
- Prerequisite
- Installation

# Prerequisite
- You need to have maven installed, and the JRE 8 in order to compile the project.
- In order to test the project, you need a mysql database.
- In order to run the application, you need a tomcat server and a mysql server.
The other way just require you to have maven

# Installation

There is two way to run this project : with or without docker.

## With docker (Continuous Integration):

you can pull the images :
mlemaile/mysql
mlemaile/mysql-test
mlemaile/tomcat
mlemaile/javamaven
mlemaile/jenkins

or build them via the dockerfile in dockerbuild/*/Dockerfile

### Setting the environnement

Once you have the image, you can run them with the command indicated in each Dockerfile. You first need to set up two network, as indicated in the below images:

![image](http://s32.postimg.org/iio0ls66t/Continuous_delivery.png)

You also need to set up two named volumes.
You can find all needed command in the file dockerbuild/binding 
You don't need to run the javamaven images, jenkins we'll run it for you, using DooD (Docker outside of Docker)

### Run the application

Just run both databases, tomcat, and jenkins. You now need to configure a bit jenkins.
Install some plugins : checkstyle, Junit, and github.
then create a new job, using the pipeline template. You just need to copy/paste the pipeline from the dockerbuild/jenkins/job_configuration.txt. Then run it, and it will deploy the application on the tomcat container.


## Without docker

### Just start it

Change the src/main/resources/hikari.properties to match your configuration.
You can use dockerbuild/mysql/*.sql files to help setting up your database.

Package the project, using "mvn -DskipTests package"
And deploy this into the tomcat container.

### Test The project :

you need a mysql database, with a user defined in it. Edit both src/test/resources/database.properties and src/test/resources/hikari.properties. You can use dockerbuild/mysqlTest/*.sql to set up the database.
Then run "mvn test"
