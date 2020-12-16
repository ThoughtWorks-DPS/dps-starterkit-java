FROM openjdk:11
ARG JAR_FILE
COPY ${JAR_FILE} starter-java.jar
COPY entrypoint.sh entrypoint.sh
ENTRYPOINT ["entrypoint.sh"]