package ru.borshchevskiy.portfolioservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.borshchevskiy.portfolioservice.model.Portfolio;

public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
}
