package ru.borshchevskiy.portfolioservice.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
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
    private TransactionTemplate transactionTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    public void prepare() {
        transactionTemplate = new TransactionTemplate(transactionManager);
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
        jdbcTemplate.execute("TRUNCATE TABLE portfolio_service.positions CASCADE ");
        jdbcTemplate.execute("TRUNCATE TABLE portfolio_service.deals CASCADE ");
        jdbcTemplate.execute("ALTER SEQUENCE portfolio_service.portfolios_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE portfolio_service.users_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE portfolio_service.positions_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE portfolio_service.deals_id_seq RESTART");
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

    @Test
    public void portfolioGetNPlusOne() {
        List<Portfolio> execute = transactionTemplate.execute(status -> {
            User user = userRepository.findById(1L).get();
            Portfolio portfolio1 = new Portfolio("portfolio1", user);
            Portfolio portfolio2 = new Portfolio("portfolio2", user);
            Portfolio portfolio3 = new Portfolio("portfolio3", user);
            Portfolio portfolio4 = new Portfolio("portfolio4", user);
            Portfolio portfolio5 = new Portfolio("portfolio5", user);
            List<Portfolio> portfolios = List.of(
                    portfolio1,
                    portfolio2,
                    portfolio3,
                    portfolio4,
                    portfolio5
            );
            for (Portfolio portfolio : portfolios) {
                Position position1 = new Position();
                Position position2 = new Position();
                position1.setPortfolio(portfolio);
                position2.setPortfolio(portfolio);
                portfolio.setPositions(List.of(
                        position1,
                        position2
                ));
            }
            return portfolioRepository.saveAll(portfolios);
        });

        System.out.println(execute.size());

        transactionTemplate.execute(status -> {
            List<Portfolio> savedPortfolios = portfolioRepository.findAll();
            for (Portfolio portfolio : savedPortfolios) {
                List<Position> positions = portfolio.getPositions();
                System.out.println(positions.size());
            }
            return null;
        });
    }
}