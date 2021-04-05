DOCKER_COMPOSE=docker-compose -f docker-compose.yml

all: build

generate_dto:
	@./mvnw compile
	@npm install --prefix ../E_User-Tech

dev_compose_start:
	@$(DOCKER_COMPOSE) up --detach

dev_compose_stop:
	@$(DOCKER_COMPOSE) stop

dev_compose_down:
	@$(DOCKER_COMPOSE) down --volumes