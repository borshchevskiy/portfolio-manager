package ru.borshchevskiy.portfolioservice.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.borshchevskiy.portfolioservice.TestcontainersBase;
import ru.borshchevskiy.portfolioservice.model.Deal;
import ru.borshchevskiy.portfolioservice.model.Portfolio;
import ru.borshchevskiy.portfolioservice.model.Position;
import ru.borshchevskiy.portfolioservice.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PortfolioRepositoryTest extends TestcontainersBase {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void prepare() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        User savedUser = userRepository.save(user);
    }

    @AfterEach
    public void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE portfolio_service.users CASCADE ");
        jdbcTemplate.execute("TRUNCATE TABLE portfolio_service.portfolios CASCADE ");
        jdbcTemplate.execute("ALTER SEQUENCE portfolios_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE users_id_seq RESTART");
    }


    @Test
    public void portfolioIsSaved() {
        Optional<User> user = userRepository.findById(1L);
        Portfolio portfolio = new Portfolio("portfolio", user.get());
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        assertNotNull(savedPortfolio.getId());
    }

    @Test
    public void portfolioIsSavedWithPositions() {
        Optional<User> user = userRepository.findById(1L);
        Portfolio portfolio = new Portfolio("portfolio", user.get());
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        assertNotNull(savedPortfolio.getId());

        Position position = new Position();
        Deal deal1 = new Deal();
        Deal deal2 = new Deal();
        position.setSecurityName("ABC");
        deal1.setSecurityName("ABC");
        deal2.setSecurityName("ABC");
        position.setDeals(List.of(
                deal1,
                deal2));
        savedPortfolio.setPositions(List.of(position));

        Portfolio portfolioWithPositions = portfolioRepository.save(savedPortfolio);

        assertNotNull(portfolioWithPositions.getPositions().get(0));
        assertNotNull(portfolioWithPositions.getPositions().get(0).getDeals().get(0));

        List<Position> positions = positionRepository.findAll();
        assertEquals(1, positions.size());

        List<Deal> deals = dealRepository.findAll();
        assertEquals(2, deals.size());
    }
}