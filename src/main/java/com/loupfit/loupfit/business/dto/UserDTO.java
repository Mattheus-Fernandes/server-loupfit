package com.loupfit.loupfit.business.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO {

    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private Long role;
}
