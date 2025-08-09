package com.loupfit.loupfit.business;

import com.loupfit.loupfit.business.converter.UserConverter;
import com.loupfit.loupfit.business.dto.user.LoginReqDTO;
import com.loupfit.loupfit.business.dto.user.LoginResDTO;
import com.loupfit.loupfit.business.dto.user.RegisterReqDTO;
import com.loupfit.loupfit.business.dto.user.UserDTO;
import com.loupfit.loupfit.infrastructure.entity.User;
import com.loupfit.loupfit.infrastructure.exceptions.ConflictException;
import com.loupfit.loupfit.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserDTO registerUser(RegisterReqDTO registerReqDTO) {
        try {
            existUsername(registerReqDTO);
            registerReqDTO.setPassword(passwordEncoder.encode(registerReqDTO.getPassword()));

            User newUser = userConverter.userEntity(registerReqDTO);

            return userConverter.userDTO(userRepository.save(newUser));
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void existUsername(RegisterReqDTO registerReqDTO) {
        try {
            boolean exist = userRepository.existsByUsername(registerReqDTO.getUsername());

            if (exist) {
                throw new ConflictException("Usuário já registrado " + registerReqDTO.getUsername());
            }

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public LoginResDTO loginUser(LoginReqDTO loginReqDTO) {
        User user = userRepository.findByUsername(loginReqDTO.getUsername()).orElseThrow(
                () -> new ConflictException("Usuário não encontrado")
        );

        System.out.println(user);

        if (!passwordEncoder.matches(loginReqDTO.getPassword(), user.getPassword())) {
            System.out.println("Senha incorreta detectada");
            throw new ConflictException("Senha incorreta");
        }

        return userConverter.loginResDTO(user);
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
        }

        if (users.isEmpty()) {
            throw new ConflictException("Nenhum usuário encontrado.");
        }

        return userConverter.userDTOList(users);
    }

    public UserDTO deleteUser(Long id) {

        validateUserAccess(id, "delete");

        User user = userRepository.findById(id).orElseThrow(
                () -> new ConflictException("Usuário não encontrado")
        );

        userRepository.deleteById(user.getId());

        return userConverter.userDTO(user);
    }

    public void isAdmin(Long role) {

        if (role != 1) {
            throw new ConflictException("Você não tem permissão para excluir este usuário");
        }
    }

    public void validateUserAccess(Long id, String methodAction) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userLogged = userRepository.findByUsername(username).orElseThrow(
                () -> new ConflictException("Usuário logado não encontrado")
        );

        isAdmin(userLogged.getRole());

        User userToDelete = userRepository.findById(id).orElseThrow(
                () -> new ConflictException("Usuário não encontrado")
        );

        if ("delete".equals(methodAction) && userLogged.getUsername().equals(userToDelete.getUsername())) {
            throw new ConflictException("Não foi possível excluir usuário");
        }
    }

    public UserDTO updateUser(Long id, RegisterReqDTO userDTO) {

       validateUserAccess(id, "put");

        User userEntity = userRepository.findById(id).orElseThrow(
                () -> new ConflictException("Usuário não encontrado")
        );

        User user = userConverter.updateUser(userDTO, userEntity);

        return userConverter.userDTO(userRepository.save(user));

    }
}
