package com.loupfit.loupfit.controller;

import com.loupfit.loupfit.business.SupplierService;
import com.loupfit.loupfit.business.dto.supplier.SupplierCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierCreateDTO> saveSupplier(@RequestBody SupplierCreateDTO supplierCreateDTO) {
        return ResponseEntity.ok(supplierService.registerSupplier(supplierCreateDTO));
    }
}
