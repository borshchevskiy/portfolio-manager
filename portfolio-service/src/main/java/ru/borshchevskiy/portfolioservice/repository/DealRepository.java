package ru.borshchevskiy.portfolioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borshchevskiy.portfolioservice.model.Deal;

public interface DealRepository extends JpaRepository<Deal, Long> {
}
