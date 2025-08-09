package com.loupfit.loupfit.infrastructure.repository;

import com.loupfit.loupfit.infrastructure.entity.StoreItem;
import com.loupfit.loupfit.infrastructure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {

    List<StoreItem> findByCreatedByUsername(String username);
    List<StoreItem> findBySupplierContainsIgnoreCase(String supplier);
    List<StoreItem> findByItemContainsIgnoreCase(String item);
}

