#! /bin/bash

docker exec -it -u root docker_kafka_1 /bin/bash -c '$KAFKA_HOME/bin/kafka-console-consumer.sh --from-beginning --bootstrap-server kafka:9092 --topic=example-entity-lifecycle'
