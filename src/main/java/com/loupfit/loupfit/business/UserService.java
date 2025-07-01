package com.loupfit.loupfit.business;

import com.loupfit.loupfit.business.converter.UserConverter;
import com.loupfit.loupfit.business.dto.UserDTO;
import com.loupfit.loupfit.infrastructure.entity.User;
import com.loupfit.loupfit.infrastructure.exceptions.ConflictException;
import com.loupfit.loupfit.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDTO registerUser(UserDTO userDTO) {
        existNickname(userDTO);

        User newUser = userConverter.userEntity(userDTO);

        return userConverter.userDTO(userRepository.save(newUser));

    }

    public void existNickname(UserDTO userDTO) {
        try {
            boolean exist = userRepository.existsByUsername(userDTO.getUsername());

            if (exist) {
                throw new ConflictException("Nickname já registrado " + userDTO.getUsername());
            }

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }
}
