package com.loupfit.loupfit.controller;

import com.loupfit.loupfit.business.UserService;
import com.loupfit.loupfit.business.dto.user.LoginReqDTO;
import com.loupfit.loupfit.business.dto.user.RegisterReqDTO;
import com.loupfit.loupfit.business.dto.user.UserDTO;
import com.loupfit.loupfit.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody RegisterReqDTO registerReqDTO) {
        return ResponseEntity.ok(userService.registerUser(registerReqDTO));
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginReqDTO loginReqDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReqDTO.getUsername(), loginReqDTO.getPassword())
        );

        return jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> filterUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Long role
    ) {

        if (name == null && username == null && role == null) {
            return ResponseEntity.ok(userService.findAllUsers());
        }

        return ResponseEntity.ok(userService.filterUsers(name, username, role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody RegisterReqDTO userDTO
    ) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }
}
