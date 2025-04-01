package com.example.Online.Shop.service.cart.impl;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.entities.Cart;
import com.example.Online.Shop.repository.jpa.CartJpaRepository;
import com.example.Online.Shop.service.cart.CartDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartDeleteServiceImpl implements CartDeleteService {
    private final CartJpaRepository cartJpaRepository;

    @Override
    public String deleteCartById(Long id) {
        Cart cart = cartJpaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(AppErrorCode.ERROR_CART_NOT_FOUND));
        cartJpaRepository.delete(cart);
        return "El carrito ha sido eliminado con éxito";
    }
}
