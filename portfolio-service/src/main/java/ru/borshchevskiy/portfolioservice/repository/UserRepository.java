package ru.borshchevskiy.portfolioservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.borshchevskiy.portfolioservice.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
