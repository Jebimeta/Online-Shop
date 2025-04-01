package com.example.Online.Shop.service.cart.impl;

import com.example.Online.Shop.repository.entities.Cart;
import com.example.Online.Shop.service.cart.CartQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartQueryServiceImpl implements CartQueryService {

	private final CartFindQueryServiceImpl cartFindQueryServiceImpl;

	@Override
	public Cart findCartById(Long id) {
		log.info("INIT - CartQueryServiceImpl -> findCartById()");

		Cart foundCart = cartFindQueryServiceImpl.findCartById(id);

		log.info("END - CartQueryServiceImpl -> findCartById() - The cart was found");
		return foundCart;
	}

}
