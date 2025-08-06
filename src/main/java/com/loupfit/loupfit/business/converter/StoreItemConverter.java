package com.loupfit.loupfit.business.converter;

import com.loupfit.loupfit.business.dto.storeitem.StoreItemCreateDTO;
import com.loupfit.loupfit.infrastructure.entity.StoreItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreItemConverter {


    public StoreItemCreateDTO storeItemReqDTO(StoreItem storeItem) {
        return StoreItemCreateDTO.builder()
                .item(storeItem.getItem())
                .quantity(storeItem.getQuantity())
                .supplier(storeItem.getSupplier())
                .observation(storeItem.getObservation())
                .build();
    }

    public StoreItem storeItemEntity(StoreItemCreateDTO storeItemReqDTO) {
        return StoreItem.builder()
                .item(storeItemReqDTO.getItem())
                .quantity(storeItemReqDTO.getQuantity())
                .supplier(storeItemReqDTO.getSupplier())
                .observation(storeItemReqDTO.getObservation())
                .build();
    }
}
