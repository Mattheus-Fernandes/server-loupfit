package com.loupfit.loupfit.business.converter;

import com.loupfit.loupfit.business.dto.storeitem.StoreItemCreateDTO;
import com.loupfit.loupfit.business.dto.storeitem.StoreItemDTO;
import com.loupfit.loupfit.infrastructure.entity.StoreItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreItemConverter {


    public StoreItemCreateDTO storeItemCreateDTO(StoreItem storeItem) {
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

    public List<StoreItemDTO> storeItemDTOList(List<StoreItem> storeItems) {

        List<StoreItemDTO> storeItemDTOS = new ArrayList<StoreItemDTO>();

        for (StoreItem storeItem : storeItems) {
            storeItemDTOS.add(storeItemDTO(storeItem));
        }

        return storeItemDTOS;
    }

    public StoreItemDTO storeItemDTO(StoreItem storeItem) {
        return StoreItemDTO.builder()
                .id(storeItem.getId())
                .item(storeItem.getItem())
                .quantity(storeItem.getQuantity())
                .supplier(storeItem.getSupplier())
                .observation(storeItem.getObservation())
                .createdBy(storeItem.getCreatedBy().getUsername())
                .build();
    }

    public StoreItem updateItem(StoreItemDTO storeItemDTO, StoreItem storeItemEntity) {

        return StoreItem.builder()
                .id(storeItemEntity.getId())
                .item(storeItemDTO.getItem() != null ? storeItemDTO.getItem() : storeItemEntity.getItem())
                .quantity(storeItemDTO.getQuantity() != null ? storeItemDTO.getQuantity() : storeItemEntity.getQuantity())
                .supplier(storeItemDTO.getSupplier() != null ? storeItemDTO.getSupplier() : storeItemEntity.getSupplier())
                .observation(storeItemDTO.getObservation() != null ? storeItemDTO.getObservation() : storeItemEntity.getObservation())
                .createdBy(storeItemEntity.getCreatedBy())
                .build();
    }
}
