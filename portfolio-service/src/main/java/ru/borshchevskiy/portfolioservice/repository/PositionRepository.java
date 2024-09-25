package ru.borshchevskiy.portfolioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borshchevskiy.portfolioservice.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
