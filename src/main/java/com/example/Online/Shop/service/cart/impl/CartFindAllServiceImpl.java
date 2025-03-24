package com.example.Online.Shop.service.cart.impl;

import com.example.Online.Shop.repository.entities.Cart;
import com.example.Online.Shop.repository.jpa.CartJpaRepository;
import com.example.Online.Shop.service.cart.CartFindAllService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartFindAllServiceImpl implements CartFindAllService {

	private final CartJpaRepository cartJpaRepository;

	@Override
	public List<Cart> findAllCarts() {
		return cartJpaRepository.findAll();
	}

}
