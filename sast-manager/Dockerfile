FROM maven:3.8.5-openjdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk
ENV TZ=Europe/Moscow

RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/${TZ} /etc/localtime && \
    echo "${TZ}" > /etc/timezone

VOLUME /tmp

COPY --from=build /app/target/sast-manager-0.0.1-SNAPSHOT.jar sast-manager-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/sast-manager-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
