package com.example.Online.Shop.service.cart.impl;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.jpa.CartJpaRepository;
import com.example.Online.Shop.service.cart.RemoveProductFromCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveProductFromCartServiceImpl implements RemoveProductFromCartService {

	private final CartJpaRepository cartJpaRepository;

	@Override
	public String removeProductFromCart(Long cartId, Long cartDetailsId) {
		try {
			cartJpaRepository.deleteProductFromCart(cartId, cartDetailsId);
			return "The product was deleted from cart successfully";
		}
		catch (BusinessException e) {
			throw new BusinessException(AppErrorCode.ERROR_PRODUCT_NOT_FOUND);
		}

	}

}
