#! /bin/bash

cmd="\$KAFKA_HOME/bin/kafka-console-consumer.sh"
cmd="${cmd} --from-beginning"
cmd="${cmd} --bootstrap-server kafka:9092"
cmd="${cmd} --topic={{cookiecutter.SERVICE_URL}}-entity-lifecycle"

docker exec -it -u root docker_kafka_1 /bin/bash -c "${cmd}"
