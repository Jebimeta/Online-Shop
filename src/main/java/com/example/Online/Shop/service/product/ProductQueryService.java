package com.example.Online.Shop.service.product;

import com.example.Online.Shop.repository.entities.Product;

public interface ProductQueryService {

    Product findProductById(Long id);

}
