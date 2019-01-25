FROM maven:3.5-jdk-8
RUN mkdir -p /service
ADD target/backend-0.0.1-SNAPSHOT.jar /service
EXPOSE 8081:8081
CMD ["/usr/bin/java -jar /service/backend-0.0.1-SNAPSHOT.jar"]