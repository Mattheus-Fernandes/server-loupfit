package com.loupfit.loupfit.controller;

import com.loupfit.loupfit.business.StoreItemService;
import com.loupfit.loupfit.business.dto.storeitem.StoreItemCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store-item")
@RequiredArgsConstructor
public class StoreItemController {

    private final StoreItemService storeItemService;

    @PostMapping
    public ResponseEntity<StoreItemCreateDTO> registerItem(@RequestBody StoreItemCreateDTO storeItemReqDTO) {
        return ResponseEntity.ok(storeItemService.createItem(storeItemReqDTO));
    }
}
