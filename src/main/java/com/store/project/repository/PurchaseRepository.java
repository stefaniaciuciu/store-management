package com.store.project.repository;

import com.store.project.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
