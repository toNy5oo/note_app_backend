FROM openjdk:17-jdk-alpine
MAINTAINER cirobozza
COPY /target/note_pp-0.0.1-SNAPSHOT.jar note_app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/note_app.jar"]