package com.example.Online.Shop.repository.jpa;

import com.example.Online.Shop.repository.entities.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailsJpaRepository extends JpaRepository<CartDetails, Long> {
}
