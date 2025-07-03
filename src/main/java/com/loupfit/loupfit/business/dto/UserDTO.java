package com.loupfit.loupfit.business.dto;


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
