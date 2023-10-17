package org.bohdan.hibernatecore.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {
    private final DataSourceConfig dataSource;

    public FlywayConfig(DataSourceConfig dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ClassicConfiguration classicConfiguration() {
        ClassicConfiguration classicConfiguration = new ClassicConfiguration();

        classicConfiguration.setDataSource(dataSource.dataSource());
        classicConfiguration.setBaselineOnMigrate(true);

        return classicConfiguration;
    }

    @Bean
    public Flyway flyway() {
        Flyway flyway = new Flyway(classicConfiguration());

        flyway.migrate();

        return flyway;
    }
}
