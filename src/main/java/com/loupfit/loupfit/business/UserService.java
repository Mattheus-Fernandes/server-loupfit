package com.loupfit.loupfit.business;

import com.loupfit.loupfit.business.converter.UserConverter;
import com.loupfit.loupfit.business.dto.LoginReqDTO;
import com.loupfit.loupfit.business.dto.LoginResDTO;
import com.loupfit.loupfit.business.dto.RegisterReqDTO;
import com.loupfit.loupfit.business.dto.UserDTO;
import com.loupfit.loupfit.infrastructure.entity.User;
import com.loupfit.loupfit.infrastructure.exceptions.ConflictException;
import com.loupfit.loupfit.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDTO registerUser(RegisterReqDTO registerReqDTO) {
        existUsername(registerReqDTO);

        User newUser = userConverter.userEntity(registerReqDTO);

        return userConverter.userDTO(userRepository.save(newUser));

    }

    public void existUsername(RegisterReqDTO registerReqDTO) {
        try {
            boolean exist = userRepository.existsByUsername(registerReqDTO.getUsername());

            if (exist) {
                throw new ConflictException("Nickname já registrado " + registerReqDTO.getUsername());
            }

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public LoginResDTO loginUser(LoginReqDTO loginReqDTO) {

        try {
           User user = userRepository.findByUsername(loginReqDTO.getUsername()).orElseThrow(
                   () -> new ConflictException("Usuário não encontrado")
           );

           if (!user.getPassword().equals(loginReqDTO.getPassword())) {
               throw new ConflictException("Senha incorreta");
           }

           return userConverter.loginResDTO(user);

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        return userConverter.userDTOList(users);
    }

    public List<UserDTO> filterUsers(String name, String username, Long role) {

        List<User> users = new ArrayList<User>();

        if (name != null && !name.trim().isEmpty()) {
            users = userRepository.findByNameContainsIgnoreCase(name);
        } else if (username != null && !username.trim().isEmpty()) {
            users = userRepository.findByUsernameContainsIgnoreCase(username);
        } else if (role != null) {
            users = userRepository.findByRole(role);
        } else {
            throw new ConflictException("Nenhum usuário encontrado.");
        }

        return userConverter.userDTOList(users);
    }
}
