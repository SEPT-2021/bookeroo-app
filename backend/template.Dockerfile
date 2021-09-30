FROM maven:3.8.2-openjdk-8

COPY . .
RUN mvn package -Dmaven.test.skip
RUN mv target/*.jar application.jar

ENTRYPOINT ["java","-jar","/application.jar"]
