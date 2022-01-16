# We use Maven
FROM maven:3.6 AS MAVEN_BUILD

COPY ./ ./

RUN mvn clean install

#arm v8 Compatiable JDK Image
FROM openjdk:18-slim-buster

COPY --from=MAVEN_BUILD ./target/shark-biz.jar /shark-biz.jar
 
# set the startup command to execute the jar
CMD ["java", "-jar", "/shark-biz.jar"]