package com.loupfit.loupfit.business.converter;

import com.loupfit.loupfit.business.dto.supplier.SupplierCreateDTO;
import com.loupfit.loupfit.business.dto.supplier.SupplierDTO;
import com.loupfit.loupfit.infrastructure.entity.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SupplierConverter {

    public SupplierCreateDTO supplierCreateDTO(Supplier supplier) {
        return SupplierCreateDTO.builder()
                .fantasyName(supplier.getFantasyName())
                .type(supplier.getType())
                .linkProfile(supplier.getLinkProfile())
                .email(supplier.getEmail())
                .site(supplier.getSite())
                .phone(supplier.getPhone())
                .build();
    }

    public SupplierDTO supplierDTO(Supplier supplier) {
        return SupplierDTO.builder()
                .id(supplier.getId())
                .fantasyName(supplier.getFantasyName())
                .type(supplier.getType())
                .linkProfile(supplier.getLinkProfile())
                .email(supplier.getEmail())
                .site(supplier.getSite())
                .phone(supplier.getPhone())
                .createdby(supplier.getCreatedBy().getUsername())
                .build();
    }

    public List<SupplierDTO> supplierDTOList(List<Supplier> suppliers) {

        List<SupplierDTO> supplierDTOList = new ArrayList<SupplierDTO>();

        for (Supplier supplier : suppliers) {
            supplierDTOList.add(supplierDTO(supplier));
        }

        return supplierDTOList;
    }

    public Supplier supplierCreateEntity(SupplierCreateDTO supplierCreateDTO) {
        return Supplier.builder()
                .fantasyName(supplierCreateDTO.getFantasyName())
                .type(supplierCreateDTO.getType())
                .linkProfile(supplierCreateDTO.getLinkProfile())
                .email(supplierCreateDTO.getEmail())
                .site(supplierCreateDTO.getSite())
                .phone(supplierCreateDTO.getPhone())
                .build();
    }
}
