package ru.borshchevskiy.portfolioservice.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.borshchevskiy.portfolioservice.TestcontainersBase;
import ru.borshchevskiy.portfolioservice.model.Portfolio;
import ru.borshchevskiy.portfolioservice.model.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PortfolioRepositoryTest extends TestcontainersBase {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void portfolioIsSaved() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        User savedUser = userRepository.save(user);
        Portfolio portfolio = new Portfolio("portfolio", savedUser);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        assertNotNull(savedPortfolio.getId());
    }
}