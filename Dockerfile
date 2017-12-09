FROM java:8-jdk-alpine
MAINTAINER Yoshio Terada

VOLUME /tmp

ADD ./target/Azure-Media-Service-1.0-SNAPSHOT.jar /app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""

RUN chmod 755 /app.jar
ENTRYPOINT java -jar app.jar
