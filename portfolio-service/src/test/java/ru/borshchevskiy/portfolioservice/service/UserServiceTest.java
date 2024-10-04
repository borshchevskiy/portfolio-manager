package ru.borshchevskiy.portfolioservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import ru.borshchevskiy.portfolioservice.dto.user.UserRequestDto;
import ru.borshchevskiy.portfolioservice.dto.user.UserResponseDto;
import ru.borshchevskiy.portfolioservice.exception.ResourceAlreadyExistsException;
import ru.borshchevskiy.portfolioservice.exception.ResourceNotFoundException;
import ru.borshchevskiy.portfolioservice.mapper.UserMapper;
import ru.borshchevskiy.portfolioservice.model.User;
import ru.borshchevskiy.portfolioservice.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("Test findByID method")
    class FindById {
        @Test
        @DisplayName("Test findById - user found successfully")
        public void userIsFound() {
            Long testId = 1L;
            User testUser = new User();
            testUser.setId(testId);

            UserResponseDto expected = new UserResponseDto();
            expected.setId(testId);

            doReturn(Optional.of(testUser)).when(userRepository).findById(testId);
            doReturn(expected).when(userMapper).toResponseDto(testUser);

            assertEquals(expected, userService.findById(testId));
        }

        @Test
        @DisplayName("Test findById when user not found - throws ResourceNotFoundException")
        public void userNotFound() {
            Long testId = 1L;

            doReturn(Optional.empty()).when(userRepository).findById(testId);

            assertThrows(ResourceNotFoundException.class, () -> userService.findById(testId));
        }
    }

    @Nested
    @DisplayName("Test create method")
    class Create {
        @Test
        @DisplayName("Test create - user is saved successfully")
        public void userIsCreated() {
            String username = "username";
            String email = "email";

            UserRequestDto requestDto = new UserRequestDto();
            requestDto.setUsername(username);
            requestDto.setEmail(email);
            UserResponseDto expected = new UserResponseDto();
            expected.setUsername(username);
            expected.setEmail(email);
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);

            doReturn(user).when(userMapper).toUser(requestDto);
            doReturn(user).when(userRepository).save(user);
            doReturn(expected).when(userMapper).toResponseDto(user);

            UserResponseDto actual = userService.create(requestDto);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("Test create when email unique constraint violated - throws ResourceAlreadyExistsException")
        public void userNotCreated() {
            String username = "username";
            String email = "email";

            UserRequestDto requestDto = new UserRequestDto();
            requestDto.setUsername(username);
            requestDto.setEmail(email);
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);

            doReturn(user).when(userMapper).toUser(requestDto);
            doThrow(DataIntegrityViolationException.class).when(userRepository).save(user);

            assertThrows(ResourceAlreadyExistsException.class, () -> userService.create(requestDto));
        }
    }

    @Nested
    @DisplayName("Test update user method")
    class Update {
        @Test
        @DisplayName("Test update - User is updated successfully")
        public void userIsUpdated() {
            Long id = 1L;
            String email = "email";
            String username = "username";
            String newEmail = "newEmail";
            String newUsername = "newUsername";

            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setUsername(username);

            UserRequestDto requestDto = new UserRequestDto();
            requestDto.setId(id);
            requestDto.setEmail(newEmail);
            requestDto.setUsername(newUsername);

            UserResponseDto expected = new UserResponseDto();
            expected.setId(id);
            expected.setEmail(newEmail);
            expected.setUsername(newUsername);

            doReturn(Optional.of(user)).when(userRepository).findById(requestDto.getId());
            doAnswer(invocation ->
            {
                User userToUpdate = invocation.getArgument(0);
                UserRequestDto request = invocation.getArgument(1);

                userToUpdate.setId(request.getId());
                userToUpdate.setUsername(request.getUsername());
                userToUpdate.setEmail(request.getEmail());

                return null;
            }).when(userMapper).updateUser(user, requestDto);
            doReturn(user).when(userRepository).save(user);
            doReturn(expected).when(userMapper).toResponseDto(user);

            UserResponseDto actual = userService.update(requestDto);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("Test when user id not found - throws ResourceNotFoundException")
        public void userIdNotFound() {
            Long id = 1L;
            String newEmail = "newEmail";
            String newUsername = "newUsername";

            UserRequestDto requestDto = new UserRequestDto();
            requestDto.setId(id);
            requestDto.setEmail(newEmail);
            requestDto.setUsername(newUsername);

            doReturn(Optional.empty()).when(userRepository).findById(requestDto.getId());

            assertThrows(ResourceNotFoundException.class, () -> userService.update(requestDto));
        }

        @Test
        @DisplayName("Test when new email is already taken - throws ResourceAlreadyExistsException")
        public void userEmailExists() {
            Long id = 1L;
            String existingEmail = "existingEmail";
            String newUsername = "newUsername";

            UserRequestDto requestDto = new UserRequestDto();
            requestDto.setId(id);
            requestDto.setEmail(existingEmail);
            requestDto.setUsername(newUsername);

            doThrow(DataIntegrityViolationException.class).when(userRepository).findById(requestDto.getId());

            assertThrows(ResourceAlreadyExistsException.class, () -> userService.update(requestDto));
        }
    }

    @Nested
    @DisplayName("Test update user's password method")
    class UpdatePassword {
        @Test
        @DisplayName("Test update password - user's password is updated successfully")
        public void userPasswordIsUpdated() {
            Long id = 1L;
            String email = "email";
            String username = "username";
            String password = "password";
            String newPassword = "newPassword";

            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);

            UserRequestDto requestDto = new UserRequestDto();
            requestDto.setId(id);
            requestDto.setEmail(email);
            requestDto.setUsername(username);
            requestDto.setPassword(newPassword);

            UserResponseDto expected = new UserResponseDto();
            expected.setId(id);
            expected.setEmail(email);
            expected.setUsername(username);

            doReturn(Optional.of(user)).when(userRepository).findById(requestDto.getId());
            doReturn(user).when(userRepository).save(user);
            doReturn(expected).when(userMapper).toResponseDto(user);

            UserResponseDto actual = userService.updatePassword(requestDto);

            assertThat(actual).isEqualTo(expected);
            assertThat(user.getPassword()).isEqualTo(newPassword);
        }

        @Test
        @DisplayName("Test when user not found - throws ResourceNotFoundException")
        void userDoesntExist() {
            Long id = 1L;
            String email = "email";
            String username = "username";
            String password = "password";

            UserRequestDto requestDto = new UserRequestDto();
            requestDto.setId(id);
            requestDto.setEmail(email);
            requestDto.setUsername(username);
            requestDto.setPassword(password);

            doReturn(Optional.empty()).when(userRepository).findById(id);

            assertThrows(ResourceNotFoundException.class, () -> userService.updatePassword(requestDto));
        }
    }

    @Nested
    @DisplayName("Test delete method")
    class Delete {
        @Test
        @DisplayName("Test delete - user is deleted successfully")
        public void userIsDeleted() {
            Integer expected = 1;
            String email = "email";

            doReturn(expected).when(userRepository).deleteByEmail(email);

            userService.delete(email);

            verify(userRepository, times(1)).deleteByEmail(email);

        }

        @Test
        @DisplayName("Test delete when email not found - throws ResourceNotFoundException")
        public void userNotCreated() {
            String email = "email";

            doReturn(null).when(userRepository).deleteByEmail(email);

            assertThrows(ResourceNotFoundException.class, () -> userService.delete(email));
        }
    }
}