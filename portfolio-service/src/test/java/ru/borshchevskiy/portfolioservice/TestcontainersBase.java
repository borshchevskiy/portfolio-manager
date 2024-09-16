package ru.borshchevskiy.portfolioservice;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class TestcontainersBase {

    @Container
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


}
