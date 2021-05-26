Niezależnie od wybranego sposobu uruchamiania kontenerów wymagane jest stworzenie sieci komendą:
+ docker network create bike-renting

Preferowany sposób obsługi kontenerów:
 + docker-compose -f docker-compose.dev.yml up --detach  <- uruchamianie
 + docker-compose -f docker-compose.dev.yml down --volumes  <- kończenie pracy (z usuwaniem danych)
 + docker-compose -f docker-compose.dev.yml stop <- zatrzymywanie kontenerów bez usuwania danych

Uruchamianie z podglądem pracy servera
 + docker-compose -f docker-compose.dev.yml up --detach mssql
 + docker-compose -f docker-compose.dev.yml up backend

Kończenie pracy analogicznie jak powyżej.
\
\
\
To run containers switch context to root and execute:
 make dev_compose_start
 
In order to stop running containers execute:
 make dev_compose_stop
 
To remove running containers, their volumes and networks execute:    
 make dev_compose_down
 
To execute flyway migrations on DEV environment run:
./scripts/flyway.sh dev migrate

In order to generate DTO definition for frontend, use:
 make generate_dto

 Some docker hacks:
 - docker-compose build - rebuilds images (using docker-compose.yml in pwd)
 - docker rm -vf $(docker ps -a -q) - purge all containers
 - docker rmi -f $(docker images -a -q) - purge all images
