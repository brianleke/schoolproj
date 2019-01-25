FROM maven:3.5-jdk-8
RUN mkdir -p /service
ADD target/backend-0.0.1-SNAPSHOT.jar /service
ADD school-backend.service /etc/systemd/system
RUN sysctl start school-backend.service
EXPOSE 8081:8081