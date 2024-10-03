package ru.borshchevskiy.portfolioservice.dto.user;

import lombok.Data;

/**
 * Class represents {@link ru.borshchevskiy.portfolioservice.model.User} data, which is sent with HTTP request.
 */
@Data
public class UserResponseDto {
    private Long id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;
}
