package com.loupfit.loupfit.business.dto.supplier;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDTO {

    private Long id;
    private String fantasyName;
    private String type;
    private String linkProfile;
    private String email;
    private String site;
    private String phone;
    private String createdby;
}
