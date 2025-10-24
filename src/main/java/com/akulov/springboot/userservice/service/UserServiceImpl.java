package com.akulov.springboot.userservice.service;

import com.akulov.springboot.userservice.dto.UserDto;
import com.akulov.springboot.userservice.entity.User;
import com.akulov.springboot.userservice.repository.UserRepository;
import com.akulov.springboot.userservice.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(MappingUtils::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(MappingUtils::mapToUserDto)
                .orElseThrow(() -> new RuntimeException("User with specified ID not found"));
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User newUser = MappingUtils.mapToUser(userDto);
        return MappingUtils.mapToUserDto(userRepository.save(newUser));
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with specified ID not found"));
        MappingUtils.updateUser(user, userDto);
        return MappingUtils.mapToUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with specified ID not found");
        }
    }

    @Override
    public List<UserDto> findAllUsersByName(String name) {;
        return userRepository.findAllByName(name).stream()
                .map(MappingUtils::mapToUserDto)
                .collect(Collectors.toList());
    }
}
