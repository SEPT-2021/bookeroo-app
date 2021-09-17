FROM openjdk:8-jdk-alpine

RUN addgroup -S bookeroo && adduser -S bookeroo-admin -G bookeroo

USER bookeroo-admin:bookeroo

VOLUME /tmp

COPY target/*.jar application.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/application.jar"]
