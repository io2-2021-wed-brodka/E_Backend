package configuration;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FlywayMigrationConfig {

    @Bean
    public static FlywayMigrationStrategy migrateStrategy(){

        return flyway -> {
            flyway.migrate();
        };
    }
}