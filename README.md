W celu uruchomienia kontenera wykonujemy:
docker-compose -f docker-compose.tests.yml up

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
