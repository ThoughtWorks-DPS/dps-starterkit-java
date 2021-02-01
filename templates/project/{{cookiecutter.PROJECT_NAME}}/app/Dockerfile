FROM openjdk:11
ARG JAR_FILE
COPY entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/entrypoint.sh
COPY ${JAR_FILE} starter-java.jar

CMD ["sh", "/usr/local/bin/entrypoint.sh"]
