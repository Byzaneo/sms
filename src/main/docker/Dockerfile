FROM openjdk:10-jre-slim

ENV START_DELAY=0 \
    JAVA_OPTS="--add-modules java.xml.bind --add-opens java.base/java.lang=ALL-UNNAMED"

# add directly the war
ADD *.war /app.war

EXPOSE 23000
CMD echo "SMS will start in ${START_DELAY}s..." && \
    sleep ${START_DELAY} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.war
