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

Create Bike Renting network:
docker network create bike-renting
