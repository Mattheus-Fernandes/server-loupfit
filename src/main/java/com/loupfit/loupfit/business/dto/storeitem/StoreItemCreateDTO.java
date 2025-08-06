package com.loupfit.loupfit.business.dto.storeitem;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreItemCreateDTO {
    private String item;
    private Integer quantity;
    private String supplier;
    private String observation;
}
