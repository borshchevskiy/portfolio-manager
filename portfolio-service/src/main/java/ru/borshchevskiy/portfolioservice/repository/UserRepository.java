package ru.borshchevskiy.portfolioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borshchevskiy.portfolioservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
