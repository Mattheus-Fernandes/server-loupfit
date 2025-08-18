package com.loupfit.loupfit.controller;

import com.loupfit.loupfit.business.SupplierService;
import com.loupfit.loupfit.business.dto.supplier.SupplierCreateDTO;
import com.loupfit.loupfit.business.dto.supplier.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierCreateDTO> saveSupplier(@RequestBody SupplierCreateDTO supplierCreateDTO) {
        return ResponseEntity.ok(supplierService.registerSupplier(supplierCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> filterSupplier(
            @RequestParam(required = false) String fantasyName,
            @RequestParam(required = false) String type
    ) {

        if (fantasyName == null && type == null) {
            return ResponseEntity.ok(supplierService.findAllSuppliers());
        }

        return ResponseEntity.ok(supplierService.filterSupplier(fantasyName, type));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SupplierDTO> deleteSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.deleteSupplier(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
    }
}
