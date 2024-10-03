package ru.borshchevskiy.portfolioservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.borshchevskiy.portfolioservice.dto.validation.groups.OnCreate;
import ru.borshchevskiy.portfolioservice.dto.validation.groups.OnPasswordUpdate;
import ru.borshchevskiy.portfolioservice.dto.validation.groups.OnUpdate;

/**
 * Class represents {@link ru.borshchevskiy.portfolioservice.model.User} data, which is received with HTTP request.
 */
@Data
public class UserRequestDto {

    private Long id;
    @NotBlank(message = "Username can't be null!", groups = {OnCreate.class, OnUpdate.class})
    @Length(message = "Username must be less than 255 symbols long!", max = 255,
            groups = {OnCreate.class, OnUpdate.class})
    private String username;
    @Length(message = "Firstname must be less than 255 symbols long!", max = 255,
            groups = {OnCreate.class, OnUpdate.class})
    private String firstname;
    @Length(message = "lastname must be less than 255 symbols long!", max = 255,
            groups = {OnCreate.class, OnUpdate.class})
    private String lastname;
    @NotBlank(message = "Email can't be null!", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "Email is not valid!", groups = {OnCreate.class, OnUpdate.class})
    @Length(message = "Email must be less than 255 symbols long!", max = 255,
            groups = {OnCreate.class, OnUpdate.class})
    private String email;
    @NotBlank(message = "Password can't be empty!", groups = {OnCreate.class, OnPasswordUpdate.class})
    @Length(message = "Password must be at least 8 symbols long!", min = 8,
            groups = {OnCreate.class, OnPasswordUpdate.class})
    @Length(message = "Password must be less than 64 symbols long!", max = 255,
            groups = {OnCreate.class, OnPasswordUpdate.class})
    private String password;
    @NotBlank(message = "Confirmation can't be empty!", groups = {OnCreate.class, OnPasswordUpdate.class})
    @Length(message = "Confirmation must be less than 64 symbols long!", max = 64,
            groups = {OnCreate.class, OnPasswordUpdate.class})
    private String passwordConfirmation;
}
