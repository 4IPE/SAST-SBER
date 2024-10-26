FROM amazoncorretto:21-alpine-jdk

VOLUME /tmp

COPY target/sast-broker-0.0.1-SNAPSHOT.jar sast-broker-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/sast-broker-0.0.1-SNAPSHOT.jar"]

EXPOSE 7070