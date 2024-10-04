package ru.borshchevskiy.portfolioservice.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.borshchevskiy.portfolioservice.TestcontainersBase;
import ru.borshchevskiy.portfolioservice.model.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest extends TestcontainersBase {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    public void prepare() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @AfterEach
    public void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE portfolio_service.users CASCADE ");
        jdbcTemplate.execute("TRUNCATE TABLE portfolio_service.portfolios CASCADE ");
        jdbcTemplate.execute("TRUNCATE TABLE portfolio_service.positions CASCADE ");
        jdbcTemplate.execute("TRUNCATE TABLE portfolio_service.deals CASCADE ");
        jdbcTemplate.execute("ALTER SEQUENCE portfolio_service.portfolios_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE portfolio_service.users_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE portfolio_service.positions_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE portfolio_service.deals_id_seq RESTART");
    }

    @Test
    public void userIsSavedAndUpdated() {
        User user = new User();
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");

        User savedUser = transactionTemplate.execute(status -> userRepository.save(user));

        System.out.println(savedUser);
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreatedDate());
        assertNotNull(savedUser.getLastModifiedDate());

        String newUsername = "new_username";
        savedUser.setUsername(newUsername);
        User updatedUser = transactionTemplate.execute(status -> userRepository.save(savedUser));
        assertEquals(newUsername, updatedUser.getUsername());
        assertTrue(updatedUser.getLastModifiedDate().isAfter(updatedUser.getCreatedDate()));
    }
}