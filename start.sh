#!/bin/bash
sleep 30s
mvn flyway:migrate -Dflyway.configFiles=./config/flyway-dev.properties
java -jar /usr/local/lib/bikeRenting.jar