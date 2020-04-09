package com.flyway.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.internal.info.MigrationInfoDumper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Strategy used to initialize {@link Flyway} migration. Custom implementations may be
 * registered as a {@code @Bean} to override the default migration behavior.
 */

@Slf4j
@Configuration
public class FlyWayConfig {
    @ConditionalOnProperty(havingValue = "clean", value = "flyway.strategy")
    @Bean
    public FlywayMigrationStrategy cleanStrategy() {
        return flyway -> flyway.clean();
    }

    @ConditionalOnProperty(havingValue = "migrate", value = "flyway.strategy")
    @Bean
    public FlywayMigrationStrategy migrationStrategy() {
        return flyway -> flyway.migrate();
    }

    @ConditionalOnProperty(havingValue = "info", value = "flyway.strategy")
    @Bean
    public FlywayMigrationStrategy infoStrategy() {
        return flyway -> {
            MigrationInfoService info = flyway.info();
            MigrationInfo current = info.current();
            MigrationVersion currentSchemaVersion = current == null ? MigrationVersion.EMPTY : current.getVersion();
            log.info("Schema version: " + currentSchemaVersion);
            log.info("");
            log.info(MigrationInfoDumper.dumpToAsciiTable(info.all()));
        };
    }

    @ConditionalOnProperty(havingValue = "validate", value = "flyway.strategy")
    @Bean
    public FlywayMigrationStrategy validationStrategy() {
        return flyway -> flyway.validate();
    }

    @ConditionalOnProperty(havingValue = "baseline", value = "flyway.strategy")
    @Bean
    public FlywayMigrationStrategy baselineStrategy() {
        return flyway -> flyway.baseline();
    }

    @ConditionalOnProperty(havingValue = "repair", value = "flyway.strategy")
    @Bean
    public FlywayMigrationStrategy repairStrategy() {
        return flyway -> flyway.repair();
    }
}
