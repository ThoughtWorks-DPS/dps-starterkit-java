FROM openjdk:11
COPY bin/entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/entrypoint.sh
COPY build/libs/*.jar /

CMD ["sh", "/usr/local/bin/entrypoint.sh"]
