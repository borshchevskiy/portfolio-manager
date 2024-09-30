package ru.borshchevskiy.portfolioservice.config.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.borshchevskiy.portfolioservice.TestcontainersBase;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SchemaInitializerTest extends TestcontainersBase {

    @Autowired
    private DataSource dataSource;

    @Value("${app.database.schema}")
    private String schemaName;

    @Test
    @DisplayName("Database schema is created successfully")
    public void DbSchemaIsCreated() throws SQLException {
        Connection connection = dataSource.getConnection();
        String actualSchemaName = connection.getSchema();
        assertEquals(schemaName, actualSchemaName);
        connection.close();
    }
}