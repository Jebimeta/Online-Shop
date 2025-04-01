package com.example.Online.Shop.service.cart.impl;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.entities.Cart;
import com.example.Online.Shop.repository.entities.Product;
import com.example.Online.Shop.repository.jpa.CartJpaRepository;
import com.example.Online.Shop.repository.jpa.ProductJpaRepository;
import com.example.Online.Shop.service.auth.AuthenticationService;
import com.example.Online.Shop.service.cart.CartFindAllService;
import com.onlineshop.domain.vo.CartDetailsRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartFindQueryServiceImpl {

    private final ProductJpaRepository productJpaRepository;

    private final CartJpaRepository cartJpaRepository;

    private final AuthenticationService authenticationService;

    public Product findProductById(CartDetailsRequest details) {
        return productJpaRepository.findById(details.getProductId()).orElseThrow(() -> {
            log.error(AppErrorCode.ERROR_PRODUCT_NOT_FOUND.getMessage());
            return new BusinessException(AppErrorCode.ERROR_PRODUCT_NOT_FOUND);
        });
    }

    @Transactional
    public Cart findCartById(Long id) {
        Cart cart = cartJpaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(AppErrorCode.ERROR_CART_NOT_FOUND));
        return checkCartInCustomerCart(cart);
    }

    private Cart checkCartInCustomerCart(Cart cart) {
        List<Cart> customerCarts = getCustomerCarts();
        validateCartInCustomerCarts(cart, customerCarts);
        return cart;
    }

    private List<Cart> getCustomerCarts() {
        List<Cart> customerCarts = authenticationService.findUserByTokenAccess().getCart();
        return customerCarts != null ? customerCarts : Collections.emptyList();
    }

    private void validateCartInCustomerCarts(Cart cart, List<Cart> customerCarts) {
        if (!customerCarts.contains(cart)) {
            log.error(AppErrorCode.ERROR_UNFORBIDDEN_CART.getMessage());
            throw new BusinessException(AppErrorCode.ERROR_UNFORBIDDEN_CART);
        }
    }

}
