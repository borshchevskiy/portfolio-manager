package ru.borshchevskiy.portfolioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borshchevskiy.portfolioservice.dto.user.UserResponseDto;
import ru.borshchevskiy.portfolioservice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Integer deleteByEmail(String email);
}
