FROM openjdk:8-jre-alpine

RUN mkdir -p /opt/conf && cd /opt 

COPY ./SMTP-client-*.jar /opt/SMTP-client.jar

CMD cd /opt && sed -i -e "s/\"hostname\": .*,/\"hostname\": \"$(eval echo \$MM_PORT_$(echo $MM_PORT | cut -d: -f3)_TCP_ADDR)\",/g" -e "s/\"port\": .*,/\"port\": $(echo $MM_PORT | cut -d: -f3),/g" ./conf/config.json && java -jar /opt/SMTP-client.jar
