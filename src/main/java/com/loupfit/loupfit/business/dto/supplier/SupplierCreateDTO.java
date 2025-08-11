package com.loupfit.loupfit.business.dto.supplier;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SupplierCreateDTO {

    private String fantasyName;
    private String type;
    private String linkProfile;
    private String email;
    private String site;
    private String phone;
}
