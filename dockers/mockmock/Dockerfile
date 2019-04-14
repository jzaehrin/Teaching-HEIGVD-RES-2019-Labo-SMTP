FROM openjdk:8-jre-alpine

RUN mkdir -p /opt && cd /opt && wget -q https://github.com/tweakers/MockMock/raw/master/release/MockMock.jar 

EXPOSE 8080 1025 

CMD java -jar /opt/MockMock.jar -p 1025 -h 8080
