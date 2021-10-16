FROM openjdk:17
ARG JAR_FILE
ARG ENTRYPOINT_FILE
COPY ${ENTRYPOINT_FILE} /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh
COPY ${JAR_FILE} /usr/local/bin/starter-java.jar
CMD ["sh", "/usr/local/bin/entrypoint.sh"]
