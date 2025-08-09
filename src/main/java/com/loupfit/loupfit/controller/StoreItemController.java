package com.loupfit.loupfit.controller;

import com.loupfit.loupfit.business.StoreItemService;
import com.loupfit.loupfit.business.dto.storeitem.StoreItemCreateDTO;
import com.loupfit.loupfit.business.dto.storeitem.StoreItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store-item")
@RequiredArgsConstructor
public class StoreItemController {

    private final StoreItemService storeItemService;

    @PostMapping
    public ResponseEntity<StoreItemCreateDTO> registerItem(@RequestBody StoreItemCreateDTO storeItemReqDTO) {
        return ResponseEntity.ok(storeItemService.createItem(storeItemReqDTO));
    }

    @GetMapping
    public ResponseEntity<List<StoreItemDTO>> getAllStoreItems(
            @RequestParam(required = false) String createdby,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) String item
    ) {

        if (createdby == null && supplier == null && item == null) {
            return ResponseEntity.ok(storeItemService.findAllStoreItens());
        }

        return ResponseEntity.ok(storeItemService.filterCreatedBy(createdby, supplier, item));

    }
}
