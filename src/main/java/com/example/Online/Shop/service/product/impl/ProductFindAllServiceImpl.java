package com.example.Online.Shop.service.product.impl;

import com.example.Online.Shop.repository.entities.Product;
import com.example.Online.Shop.repository.jpa.ProductJpaRepository;
import com.example.Online.Shop.service.product.ProductsFindAllService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductFindAllServiceImpl implements ProductsFindAllService {

	private final ProductJpaRepository productJpaRepository;

	@Override
	public List<Product> findAllProducts() {
		log.info("Init - ProductFindAllServiceImpl -> findAllProducts()");
		List<Product> obtainedProducts = productJpaRepository.findAll();
		for (Product product : obtainedProducts) {
			log.debug("Product - {}", product.getName());
		}
		log.info("End - ProductFindAllServiceImpl -> findAllProducts()");
		return obtainedProducts;
	}

}
