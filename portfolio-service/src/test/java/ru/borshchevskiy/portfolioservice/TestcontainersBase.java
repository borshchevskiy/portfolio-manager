package ru.borshchevskiy.portfolioservice;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;


public abstract class TestcontainersBase {

    @ServiceConnection
    public static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.4"))
                    .withDatabaseName("portfolio_service")
                    .withUrlParam("currentSchema", "portfolio_service");

    @Container
    @ServiceConnection(name = "redis")
    public static GenericContainer<?> redisContainer =
            new GenericContainer<>(DockerImageName.parse("redis:latest"))
                    .withExposedPorts(6379);

    @BeforeAll
    static void startContainer() {
        postgresContainer.start();
        redisContainer.start();
    }
}
