package com.nandonescorp.api.service;

import com.nandonescorp.api.dto.UserRequestDTO;
import com.nandonescorp.api.mapper.UserMapper;
import com.nandonescorp.api.model.User;
import com.nandonescorp.api.repository.UserRepository;
import com.nandonescorp.api.dto.UserResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(UserRequestDTO dto) {
        User user = User.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        User saved = userRepository.save(user);
        return UserMapper.toDTO(saved);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
