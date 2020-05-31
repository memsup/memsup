package com.github.memsup.configuration;

import com.github.enesusta.jdbc.datasource.HikariJdbcDataSource;
import com.github.enesusta.jdbc.datasource.JdbcConfiguration;
import com.github.enesusta.jdbc.datasource.JdbcDataSource;
import com.github.enesusta.jdbc.datasource.JdbcOption;
import com.github.enesusta.jdbc.datasource.enums.ConnectionOptions;
import com.github.enesusta.jdbc.datasource.enums.DatabaseType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
@PropertySource("classpath:application.yaml")
public class PostgreDataSourceConfiguration {

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String pwd;

    @Bean
    public DataSource dataSource() {

        final JdbcConfiguration configuration = new JdbcConfiguration.JdbcConfigurationBuilder()
                .username(userName)
                .password(pwd)
                .type(DatabaseType.POSTGRE)
                .selectedDatabase("docker")
                .options(
                        Collections.singletonList(new JdbcOption(ConnectionOptions.CHARACTER_ENCODING, "utf8"))
                )
                .build();

        final JdbcDataSource dataSource = new HikariJdbcDataSource(configuration);
        return dataSource.getDataSource();

    }

}
