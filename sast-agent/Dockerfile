FROM amazoncorretto:21-alpine-jdk

RUN apk add --no-cache curl unzip

VOLUME /tmp

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

RUN apk add --no-cache curl unzip maven

ENV CLASSPATH="/spotbugs/lib/spotbugs.jar"

RUN mkdir -p /dir_tmp && chmod -R 777 /dir_tmp

COPY target/sast-agent-0.0.1-SNAPSHOT.jar sast-agent-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/sast-agent-0.0.1-SNAPSHOT.jar"]

EXPOSE 6060
