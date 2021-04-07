#!/usr/bin/env bash

SQLCMD=/opt/mssql-tools/bin/sqlcmd
INIT_TEMPLATE_FILE=/opt/init-template.sql
INIT_FILE=/tmp/init.sql

/opt/mssql/bin/sqlservr &
SERVER_PID=$!

while ! nc -z localhost 1433 >/dev/null 2>&1; do
  echo 'Waiting for SQL Server to accept connections...'
  sleep 1
done
echo 'SQL Server ready'

if [ -z ${DATABASE_PASSWORD} ]; then
  echo 'DATABASE_PASSWORD environmental variable not set!'
  exit 1
fi

if [ ! -f ${INIT_FILE} ]; then
  echo 'Building initialization script...'
  cp ${INIT_TEMPLATE_FILE} ${INIT_FILE}
  envsubst '${DATABASE_PASSWORD}' <${INIT_TEMPLATE_FILE} >${INIT_FILE}

  echo 'Executing initialization script...'
  if ! ${SQLCMD} -S localhost -U sa -P ${SA_PASSWORD} -i ${INIT_FILE}; then
    echo 'Initialization script failed!'
    rm ${INIT_FILE}
    exit 1
  else
    echo 'Initialization script executed'
  fi
fi

wait ${SERVER_PID}
