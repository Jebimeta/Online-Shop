package com.example.Online.Shop.service.cart;

import com.example.Online.Shop.repository.entities.CartDetails;
import com.onlineshop.domain.vo.CartDetailsRequest;

public interface AddProductToCartService {

	CartDetails addProductCart(Long cartId, CartDetailsRequest request);

}
