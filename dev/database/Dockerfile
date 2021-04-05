FROM mcr.microsoft.com/mssql/server:2019-CU8-ubuntu-16.04

ENV DATABASE_PASSWORD ""

USER root
RUN apt-get update && apt-get install -y netcat gettext-base && rm -rf /var/lib/apt/lists

WORKDIR /opt

COPY entrypoint.sh .
RUN chmod +x entrypoint.sh

COPY init-template.sql .

USER mssql
CMD ["/opt/entrypoint.sh"]
