DOCKER_COMPOSE=docker-compose -f docker-compose.yml

all: build

generate_dto:
	@./mvnw compile
	@npm install --prefix ../E_User-Tech
	@npm install --prefix ../E_User-Tech
	@rm -rf ../E_Admin/src/app/generated
	@mkdir ../E_Admin/src/app/generated && cp ../E_User-Tech/src/app/generated/dto.ts ../E_Admin/src/app/generated/dto.ts

dev_compose_start:
	@$(DOCKER_COMPOSE) up --detach

dev_compose_stop:
	@$(DOCKER_COMPOSE) stop

dev_compose_down:
	@$(DOCKER_COMPOSE) down --volumes

maven_build_no_tests:
	@mvn -B package --file pom.xml -Dskip.npm -Dmaven.test.skip=true

maven_run:
	@mvn spring-boot:run -f pom.xml

check_makefile:
	@cat -e -t -v Makefile