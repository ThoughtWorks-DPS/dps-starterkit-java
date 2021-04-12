FROM alpine:3.7

RUN apk --no-cache add curl
CMD [ "/usr/local/bin/install-policy.sh" ]
COPY install-policy.sh /usr/local/bin
COPY policy.rego /
