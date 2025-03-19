package com.example.Online.Shop.repository.jpa;

import com.example.Online.Shop.repository.entities.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface CartJpaRepository extends JpaRepository<Cart, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CartDetails cd WHERE cd.cart.id = :cartId AND cd.id = :cartDetailsId")
    void deleteProductFromCart(@Param("cartId") Long cartId, @Param("cartDetailsId") Long cartDetailsId);
}
