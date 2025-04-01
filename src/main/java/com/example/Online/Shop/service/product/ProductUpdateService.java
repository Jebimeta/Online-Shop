package com.example.Online.Shop.service.product;

import com.example.Online.Shop.repository.entities.Product;

public interface ProductUpdateService {

	Product updateProduct(Long id, Product product);

}
