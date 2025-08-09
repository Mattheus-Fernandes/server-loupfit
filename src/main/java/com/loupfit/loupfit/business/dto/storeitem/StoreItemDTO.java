package com.loupfit.loupfit.business.dto.storeitem;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreItemDTO {

    private Long id;
    private String item;
    private Integer quantity;
    private String supplier;
    private String observation;
    private String createdBy;
}
