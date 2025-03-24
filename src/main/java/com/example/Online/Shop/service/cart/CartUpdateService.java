package com.example.Online.Shop.service.cart;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.repository.entities.Cart;

public interface CartUpdateService {

	Cart update(Long cartId, Cart cartRequest) throws BusinessException;

}
