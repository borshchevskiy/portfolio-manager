package ru.borshchevskiy.portfolioservice.config.database;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.borshchevskiy.portfolioservice.exception.InitializationException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DbSchemaInitializer implements InitializingBean {
    @Autowired
    private DataSource dataSource;
    @Value("${app.database.schema}")
    private String schemaName;

    @Override
    public void afterPropertiesSet() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("CREATE SCHEMA IF NOT EXISTS %s", schemaName));
        } catch (SQLException e) {
            throw new InitializationException("Failed to create database schema '" + schemaName + "'.", e);
        }
    }
}
