package com.loupfit.loupfit.controller;

import com.loupfit.loupfit.business.UserService;
import com.loupfit.loupfit.business.dto.LoginReqDTO;
import com.loupfit.loupfit.business.dto.LoginResDTO;
import com.loupfit.loupfit.business.dto.RegisterReqDTO;
import com.loupfit.loupfit.business.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody RegisterReqDTO registerReqDTO) {
        return ResponseEntity.ok(userService.registerUser(registerReqDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> loginUser(@RequestBody LoginReqDTO loginReqDTO) {
        return ResponseEntity.ok(userService.loginUser(loginReqDTO));
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

    @DeleteMapping("{id}")
    public ResponseEntity<UserDTO> deleteUser(
            @PathVariable Long id,
            @RequestHeader("X-User-Role") Long role
    ) {
        return ResponseEntity.ok(userService.deleteUser(role, id));
    }
}
