FROM amazoncorretto:21-alpine-jdk

VOLUME /tmp

COPY target/sast-service-0.0.1-SNAPSHOT.jar sast-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/sast-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 9090