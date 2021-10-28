FROM maven:3.8.3-openjdk-8

COPY pom.xml .
RUN ["mvn", "verify", "clean", "--fail-never"]
COPY . .
RUN mvn package -Dmaven.test.skip
RUN mv target/*.jar application.jar

ENTRYPOINT ["java","-jar","/application.jar"]
