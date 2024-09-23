package ru.borshchevskiy.portfolioservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.borshchevskiy.portfolioservice.model.Position;

public interface PositionRepository extends CrudRepository<Position, Long> {
}
