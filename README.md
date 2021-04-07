[![Java CI with Maven](https://github.com/io2-2021-wed-brodka/E_Backend/actions/workflows/maven.yml/badge.svg)](https://github.com/io2-2021-wed-brodka/E_Backend/actions/workflows/maven.yml)

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
