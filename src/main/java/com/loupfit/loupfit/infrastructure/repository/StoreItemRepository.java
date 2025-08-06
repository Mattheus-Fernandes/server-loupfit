package com.loupfit.loupfit.infrastructure.repository;

import com.loupfit.loupfit.infrastructure.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {
}

