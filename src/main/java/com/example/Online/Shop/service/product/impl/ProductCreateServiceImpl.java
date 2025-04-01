package com.example.Online.Shop.service.product.impl;

import com.example.Online.Shop.repository.entities.Product;
import com.example.Online.Shop.repository.jpa.ProductJpaRepository;
import com.example.Online.Shop.service.product.ProductCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCreateServiceImpl implements ProductCreateService {

	private final ProductJpaRepository productJpaRepository;

	@Override
	public Product createProduct(Product product) {
		log.info("Init - ProductCreateServiceImpl -> createProduct()");
		Product createdProduct = productJpaRepository.save(product);
		log.info("End - ProductCreateServiceImpl -> createProduct() - Product: {}", createdProduct.getName());
		return createdProduct;
	}

}
