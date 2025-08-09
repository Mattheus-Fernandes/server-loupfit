package com.loupfit.loupfit.business.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginReqDTO {

    private String username;
    private String password;
}
