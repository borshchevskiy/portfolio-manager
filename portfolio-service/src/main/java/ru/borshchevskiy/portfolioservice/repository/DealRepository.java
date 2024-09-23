package ru.borshchevskiy.portfolioservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.borshchevskiy.portfolioservice.model.Deal;

public interface DealRepository extends CrudRepository<Deal, Long> {
}
