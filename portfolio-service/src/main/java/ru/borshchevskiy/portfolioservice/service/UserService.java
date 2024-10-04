package ru.borshchevskiy.portfolioservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.borshchevskiy.portfolioservice.dto.user.UserRequestDto;
import ru.borshchevskiy.portfolioservice.dto.user.UserResponseDto;
import ru.borshchevskiy.portfolioservice.exception.ResourceAlreadyExistsException;
import ru.borshchevskiy.portfolioservice.exception.ResourceNotFoundException;
import ru.borshchevskiy.portfolioservice.mapper.UserMapper;
import ru.borshchevskiy.portfolioservice.model.User;
import ru.borshchevskiy.portfolioservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private static final String USER_WITH_EMAIL_EXISTS = "User with email %s already exists!";
    private static final String USER_WITH_ID_EXISTS = "User with id %d already exists!";
    private static final String USER_WITH_EMAIL_NOT_FOUND = "User with email %s not found.";

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format(USER_WITH_ID_EXISTS, id)));
    }

    @Transactional
    public UserResponseDto create(UserRequestDto userRequestDto) {
        try {
            User user = userRepository.save(userMapper.toUser(userRequestDto));
            return userMapper.toResponseDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(
                    String.format(USER_WITH_EMAIL_EXISTS, userRequestDto.getEmail()), e);
        }
    }

    @Transactional
    public UserResponseDto update(UserRequestDto userRequestDto) {
        try {
            return userRepository.findByEmail(userRequestDto.getEmail())
                    .map(user ->
                    {
                        userMapper.updateUser(user, userRequestDto);
                        return user;
                    })
                    .map(userRepository::save)
                    .map(userMapper::toResponseDto)
                    .orElseThrow(() ->
                    {
                        throw new ResourceNotFoundException(
                                String.format(USER_WITH_EMAIL_NOT_FOUND, userRequestDto.getEmail()));
                    });
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(
                    String.format(USER_WITH_EMAIL_EXISTS, userRequestDto.getEmail()), e);
        }
    }

    @Transactional
    public UserResponseDto updatePassword(UserRequestDto userRequestDto) {
        return userRepository.findByEmail(userRequestDto.getEmail())
                .map(user ->
                {
                    user.setPassword((userRequestDto.getPassword()));
                    return user;
                })
                .map(userRepository::save)
                .map(userMapper::toResponseDto)
                .orElseThrow(() ->
                {
                    throw new ResourceNotFoundException(
                            String.format(USER_WITH_EMAIL_NOT_FOUND, userRequestDto.getEmail()));
                });
    }

    @Transactional
    public UserResponseDto delete(String email) {
        return userRepository.deleteByEmail(email)
                .map(userMapper::toResponseDto)
                .orElseThrow(() ->
                {
                    throw new ResourceNotFoundException(
                            String.format(USER_WITH_EMAIL_NOT_FOUND, email));
                });
    }
}
