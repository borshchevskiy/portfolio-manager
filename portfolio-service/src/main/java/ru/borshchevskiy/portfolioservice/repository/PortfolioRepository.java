package ru.borshchevskiy.portfolioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borshchevskiy.portfolioservice.model.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
