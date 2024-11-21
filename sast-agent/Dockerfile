FROM amazoncorretto:20-alpine-jdk

RUN apk add --no-cache curl unzip

VOLUME /tmp

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# Загрузка SpotBugs
RUN curl -L -o spotbugs.zip https://repo.maven.apache.org/maven2/com/github/spotbugs/spotbugs/4.7.3/spotbugs-4.7.3.zip && \
    unzip -d /spotbugs spotbugs.zip && \
    mv /spotbugs/spotbugs-4.7.3/* /spotbugs/ && \
    rm -rf spotbugs.zip /spotbugs/spotbugs-4.7.3 && \
    chmod -R 755 /spotbugs

RUN apk add --no-cache curl unzip maven

ENV CLASSPATH="/spotbugs/lib/spotbugs.jar"

RUN mkdir -p /dir_tmp && chmod -R 777 /dir_tmp

COPY target/sast-agent-0.0.1-SNAPSHOT.jar sast-agent-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/sast-agent-0.0.1-SNAPSHOT.jar"]

EXPOSE 6060
