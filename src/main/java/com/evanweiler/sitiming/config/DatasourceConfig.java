package com.evanweiler.sitiming.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories (basePackages = {"com.evanweiler.sitiming.repository"})
public class DatasourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource.sitiming")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
