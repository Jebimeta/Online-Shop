package com.example.Online.Shop.service.cart.impl;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.entities.Cart;
import com.example.Online.Shop.repository.jpa.CartDetailsJpaRepository;
import com.example.Online.Shop.repository.jpa.CartJpaRepository;
import com.example.Online.Shop.service.cart.CartCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartCreateServiceImpl implements CartCreateService {

    private final CartJpaRepository cartJpaRepository;

    private final CartDetailsJpaRepository cartDetailsJpaRepository;

    @Override
    public Cart createCartWithCartDetails(Cart cart) {
        log.info("INIT - CartCreateServiceImpl -> createCartWithCartDetails()");

        if (!cart.getCartDetails().isEmpty()) {
            log.info("Saving the cartDetails.");
            cartDetailsJpaRepository.saveAll(cart.getCartDetails());
        }
        else {
            throw new BusinessException(AppErrorCode.ERROR_CREATE_CART);
        }
        log.info("Saving the new shopping cart.");
        Cart createdCart = cartJpaRepository.save(cart);
        log.info("END - CartCreateServiceImpl -> createCartWithCartDetails()");
        return createdCart;
    }
}
