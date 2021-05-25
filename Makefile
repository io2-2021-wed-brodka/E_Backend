DOCKER_COMPOSE_DEV=docker-compose -f docker-compose.dev.yml
DOCKER_COMPOSE_TESTS=docker-compose -f docker-compose.tests.yml
DOCKER_BUILD=docker build

all: build

generate_dto:
	@./mvnw compile
	@npm install --prefix ../E_User-Tech
	@npm install --prefix ../E_User-Tech
	@rm -rf ../E_Admin/src/app/generated
	@mkdir ../E_Admin/src/app/generated && cp ../E_User-Tech/src/app/generated/dto.ts ../E_Admin/src/app/generated/dto.ts

dev_compose_start:
	@$(DOCKER_COMPOSE_DEV) up --detach

dev_compose_stop:
	@$(DOCKER_COMPOSE_DEV) stop

docker_build_backend:
    @$(DOCKER_BUILD) -t niezly-backend-pl .

maven_build_no_tests:
	@mvn -B package --file pom.xml -Dskip.npm -Dmaven.test.skip=true

maven_run:
	@mvn spring-boot:run -f pom.xml

maven_clean_build_run_no_tests:
	@mvn clean
	@mvn -B package --file pom.xml -Dskip.npm -Dmaven.test.skip=true
	@mvn spring-boot:run -f pom.xml

flyway_migrate_dev:
	@mvn flyway:migrate -Dflyway.configFiles=./config/flyway-dev.properties

flyway_migrate_unit_tests:
	@mvn flyway:migrate -Dflyway.configFiles=./config/flyway-unit-tests.properties

check_makefile:
	@cat -e -t -v Makefile