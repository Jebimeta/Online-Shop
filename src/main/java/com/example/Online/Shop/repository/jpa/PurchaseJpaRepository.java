package com.example.Online.Shop.repository.jpa;

import com.example.Online.Shop.repository.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseJpaRepository extends JpaRepository<Purchase, Long> {

}
