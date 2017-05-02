FROM jenkins:2.46.1

##trying to install automatically plugins.
#USER jenkins
#COPY plugins.txt /usr/share/jenkins/plugins.txt
#RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

USER root
RUN apt-get update \
      && apt-get install -y sudo \
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
RUN groupadd -g 999 docker && usermod -a -G docker jenkins
# CHANGER LE GROUP ID '994' avec le groupe ID de docker


USER jenkins

#docker run -d --name=jenkins -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -p 8282:8080 -v test:/test -v prod:/prod jenkins