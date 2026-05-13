package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.UserRepository;
import org.example.dto.UserReadDto;
import org.example.entity.User;
import org.example.mapper.UserReadMapper;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;

    public boolean delete(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(id));
        return maybeUser.isPresent();
    }

    public Optional<UserReadDto> findUserById(Long id) {
        return userRepository.findById(id).map(userReadMapper::mapFrom);
    }
}
