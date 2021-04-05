DOCKER_COMPOSE=docker-compose -f docker-compose.yml

all: build

dev_prepare: clean dev_refresh

dev_refresh:
	@./mvnw compile
	@npm install --prefix frontend

dev_compose_start:
	@$(DOCKER_COMPOSE) up --detach

dev_compose_stop:
	@$(DOCKER_COMPOSE) stop

dev_compose_down:
	@$(DOCKER_COMPOSE) down --volumes