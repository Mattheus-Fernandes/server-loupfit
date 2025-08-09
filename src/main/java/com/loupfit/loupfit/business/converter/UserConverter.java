package com.loupfit.loupfit.business.converter;

import com.loupfit.loupfit.business.dto.user.LoginResDTO;
import com.loupfit.loupfit.business.dto.user.RegisterReqDTO;
import com.loupfit.loupfit.business.dto.user.UserDTO;
import com.loupfit.loupfit.infrastructure.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public UserDTO userDTO(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public List<UserDTO> userDTOList(List<User> users) {

        List<UserDTO> userDTOS = new ArrayList<UserDTO>();

        for (User user: users) {
            userDTOS.add(userDTO(user));
        }

        return userDTOS;

    }

    public User userEntity(RegisterReqDTO registerReqDTO) {
        return User.builder()
                .name(registerReqDTO.getName())
                .lastname(registerReqDTO.getLastname())
                .username(registerReqDTO.getUsername())
                .password(registerReqDTO.getPassword())
                .role(registerReqDTO.getRole())
                .build();
    }

    public LoginResDTO loginResDTO(User user) {
        return LoginResDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public User updateUser(RegisterReqDTO userDTO, User userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userDTO.getName() != null ? userDTO.getName() : userEntity.getName())
                .lastname(userDTO.getLastname() != null ? userDTO.getLastname() : userEntity.getLastname())
                .username(userDTO.getUsername() != null ? userDTO.getUsername() : userEntity.getUsername())
                .password(userDTO.getPassword() != null ? userDTO.getPassword() : userEntity.getPassword())
                .role(userDTO.getRole() != null ? userDTO.getRole() : userEntity.getRole())
                .build();
    }
}
