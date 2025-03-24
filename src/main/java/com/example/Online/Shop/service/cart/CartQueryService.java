package com.example.Online.Shop.service.cart;

import com.example.Online.Shop.repository.entities.Cart;

public interface CartQueryService {

	Cart findCartById(Long id);

}
