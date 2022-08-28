package com.example.Netflix.Config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Value("jdbcUrl")
    private String jdbcUrl;

    @Value("dbUserName")
    private String dbUserName;

    @Value("dbPassword")
    private String dbPassword;


    @Bean(destroyMethod = "close")
    @Primary
    DataSource getDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        return dataSource;
    }
}
