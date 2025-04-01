package com.example.Online.Shop.service.cart.impl;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.entities.Cart;
import com.example.Online.Shop.repository.jpa.CartJpaRepository;
import com.example.Online.Shop.service.cart.CartUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartUpdateServiceImpl implements CartUpdateService {

	private final CartJpaRepository cartJpaRepository;

	@Override
	public Cart update(Long cartId, Cart cartRequest) throws BusinessException {
		validateCartRequest(cartRequest);

		Cart existingCart = cartJpaRepository.findById(cartId)
			.orElseThrow(() -> new BusinessException(AppErrorCode.ERROR_CART_NOT_FOUND));

		existingCart.setDate(cartRequest.getDate());
		existingCart.setCustomer(cartRequest.getCustomer());
		existingCart.setCartDetails(existingCart.getCartDetails());

		return cartJpaRepository.save(existingCart);
	}

	private void validateCartRequest(Cart cartRequest) throws BusinessException {
		if (cartRequest == null) {
			throw new BusinessException(AppErrorCode.ERROR_INVALID_CART_REQUEST);
		}

	}

}
