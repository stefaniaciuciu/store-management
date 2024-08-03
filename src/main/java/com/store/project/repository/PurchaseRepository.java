package com.store.project.repository;

import com.store.project.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByProductId(Long productId);
    List<Purchase> findByUserId(Long userId);
}
