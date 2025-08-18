package com.loupfit.loupfit.business.dto.user;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO {

    private String name;
    private String lastname;
    private String username;
    private Long role;
}
