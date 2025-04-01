package com.example.Online.Shop.service.cart.impl;

import com.example.Online.Shop.repository.entities.Cart;
import com.example.Online.Shop.repository.entities.CartDetails;
import com.example.Online.Shop.repository.jpa.CartJpaRepository;
import com.example.Online.Shop.service.cart.AddProductToCartService;
import com.onlineshop.domain.vo.CartDetailsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddProductToCartServiceImpl implements AddProductToCartService {

	private final CartJpaRepository cartJpaRepository;

	private final CartFindQueryServiceImpl cartFindQueryServiceImpl;

	@Override
	public CartDetails addProductCart(Long cartId, CartDetailsRequest request) {

		return null;
	}

}
