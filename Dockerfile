FROM openjdk:8-jdk-alpine

LABEL maintainer="Andre Rocha <devel.andrerocha@gmail.com>"

RUN mkdir -p /var/lib/thinkstep

WORKDIR /var/lib/thinkstep

COPY devops/docker/docker-entrypoint.sh /var/lib/thinkstep/
COPY build/libs/online-users-1.0.jar /var/lib/thinkstep/
RUN chmod 777 /var/lib/thinkstep/docker-entrypoint.sh && \
    chmod 777 /var/lib/thinkstep/online-users-1.0.jar

#start
EXPOSE 8080
ENTRYPOINT ["/var/lib/thinkstep/docker-entrypoint.sh"]