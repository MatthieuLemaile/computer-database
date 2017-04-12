FROM maven:3.3-jdk-8

RUN cd /opt && wget "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-linux-x86_64.tar.bz2" && tar -xf phantomjs-2.1.1-linux-x86_64.tar.bz2
CMD ls /opt

#docker run -it --name=javamaven --net=test-network --ip=172.18.0.3 javamaven
