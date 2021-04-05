#!/usr/bin/env bash
TYPE="$1"
COMMAND="$2"

if [ -z "$TYPE" ] || [ -z "$COMMAND" ]; then
    echo "Executes Flyway's COMMAND on the database of type TYPE"
    echo ""
    echo "USAGE: ./flyway.sh TYPE COMMAND"
    echo ""
    echo "TYPE - check config catalogue"
    echo "COMMAND - migrate, clean, info, validate, baseline, repair"
else
    ./mvnw flyway:${COMMAND} -Dflyway.configFiles=config/flyway-${TYPE}.properties
fi

