package com.loupfit.loupfit.business.converter;

import com.loupfit.loupfit.business.dto.LoginResDTO;
import com.loupfit.loupfit.business.dto.UserDTO;
import com.loupfit.loupfit.infrastructure.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public UserDTO userDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public User userEntity(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .lastname(userDTO.getLastname())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
    }

    public LoginResDTO loginResDTO(UserDTO userDTO) {
        return LoginResDTO.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .build();
    }
}
