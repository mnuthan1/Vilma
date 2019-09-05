FROM openjdk:11-slim
MAINTAINER  Nuthan Kumar <mnuthan@gmail.com>
COPY ./build/libs/filestore-0.0.1-SNAPSHOT.jar /core_app.jar
EXPOSE 9090
CMD java -Djava.security.egd=file:/dev/./urandom -jar /core_app.jar