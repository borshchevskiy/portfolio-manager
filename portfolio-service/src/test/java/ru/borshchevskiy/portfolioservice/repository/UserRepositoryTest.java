package ru.borshchevskiy.portfolioservice.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.borshchevskiy.portfolioservice.TestcontainersBase;
import ru.borshchevskiy.portfolioservice.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest extends TestcontainersBase {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userIsSavedAndUpdated() {
        User user = new User();
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreatedDate());
        assertNotNull(savedUser.getLastModifiedDate());
        assertEquals(savedUser.getCreatedDate(), savedUser.getLastModifiedDate());

        String newUsername = "new_username";
        savedUser.setUsername(newUsername);
        User updatedUser = userRepository.save(savedUser);
        assertEquals(newUsername, updatedUser.getUsername());
        assertTrue(updatedUser.getLastModifiedDate().isAfter(updatedUser.getCreatedDate()));
    }
}