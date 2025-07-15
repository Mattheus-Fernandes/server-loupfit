package com.loupfit.loupfit.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResDTO {

    private Long id;
    private String username;
    private Long role;
}
