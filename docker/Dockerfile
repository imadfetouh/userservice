FROM openjdk:11
EXPOSE 8087
ARG JAR_FILE=/userservice/target/userservice-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]