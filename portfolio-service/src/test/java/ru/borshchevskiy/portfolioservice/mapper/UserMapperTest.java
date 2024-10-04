package ru.borshchevskiy.portfolioservice.mapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.borshchevskiy.portfolioservice.dto.user.UserRequestDto;
import ru.borshchevskiy.portfolioservice.dto.user.UserResponseDto;
import ru.borshchevskiy.portfolioservice.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void entityConvertsToDto() {
        Long testId = 1L;
        String testUsername = "username";
        String testFirstname = "firstname";
        String testLastname = "lastname";
        String testEmail = "email@test.com";

        User user = new User();
        user.setId(testId);
        user.setUsername(testUsername);
        user.setFirstname(testFirstname);
        user.setLastname(testLastname);
        user.setEmail(testEmail);

        UserResponseDto expected = new UserResponseDto();
        expected.setId(testId);
        expected.setUsername(testUsername);
        expected.setFirstname(testFirstname);
        expected.setLastname(testLastname);
        expected.setEmail(testEmail);

        UserResponseDto actual = userMapper.toResponseDto(user);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void dtoConvertToEntity() {
        Long testId = 1L;
        String testUsername = "username";
        String testFirstname = "firstname";
        String testLastname = "lastname";
        String testEmail = "email@test.com";

        User expected = new User();
        expected.setId(testId);
        expected.setUsername(testUsername);
        expected.setFirstname(testFirstname);
        expected.setLastname(testLastname);
        expected.setEmail(testEmail);

        UserRequestDto request = new UserRequestDto();
        request.setId(testId);
        request.setUsername(testUsername);
        request.setFirstname(testFirstname);
        request.setLastname(testLastname);
        request.setEmail(testEmail);

        User actual = userMapper.toUser(request);

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
        assertThat(actual.getFirstname()).isEqualTo(expected.getFirstname());
        assertThat(actual.getLastname()).isEqualTo(expected.getLastname());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }
}