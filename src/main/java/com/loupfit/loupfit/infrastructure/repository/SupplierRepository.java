package com.loupfit.loupfit.infrastructure.repository;

import com.loupfit.loupfit.infrastructure.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByFantasyNameContainsIgnoreCase(String fantasyName);
    List<Supplier> findByFantasyNameContainsIgnoreCase(String fantasyName);
    List<Supplier> findByTypeContainsIgnoreCase(String type);
}
