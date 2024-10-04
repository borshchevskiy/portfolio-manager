package ru.borshchevskiy.portfolioservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.borshchevskiy.portfolioservice.dto.user.UserRequestDto;
import ru.borshchevskiy.portfolioservice.dto.user.UserResponseDto;
import ru.borshchevskiy.portfolioservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(User user);
    User toUser(UserRequestDto userRequestDto);
    void updateUser(@MappingTarget User user, UserRequestDto userRequestDto);
}
