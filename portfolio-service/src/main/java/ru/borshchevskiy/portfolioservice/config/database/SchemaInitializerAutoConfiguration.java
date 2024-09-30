package ru.borshchevskiy.portfolioservice.config.database;

import liquibase.change.DatabaseChange;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.stereotype.Component;
import ru.borshchevskiy.portfolioservice.exception.InitializationException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@AutoConfiguration(
        after = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
        before = {LiquibaseAutoConfiguration.class}
)
@ConditionalOnClass({SpringLiquibase.class, DatabaseChange.class})
@ConditionalOnProperty(prefix = "spring.liquibase", name = "enabled", matchIfMissing = true)
public class SchemaInitializerAutoConfiguration {

    @Component
    @RequiredArgsConstructor
    public static class SchemaInitializerBean implements InitializingBean {
        private final DataSource dataSource;
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

    @Component
    @ConditionalOnBean(SchemaInitializerBean.class)
    public static class SpringLiquibaseDependsOnPostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {

        protected SpringLiquibaseDependsOnPostProcessor() {
            super(SpringLiquibase.class, SchemaInitializerBean.class);
        }
    }
}
