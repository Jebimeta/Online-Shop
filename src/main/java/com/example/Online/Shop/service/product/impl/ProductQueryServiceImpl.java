package com.example.Online.Shop.service.product.impl;

import com.example.Online.Shop.repository.entities.Product;
import com.example.Online.Shop.repository.jpa.ProductJpaRepository;
import com.example.Online.Shop.service.product.ProductQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

	private final ProductJpaRepository productJpaRepository;

	@Override
	public Product findProductById(Long id) {
		log.info("Init - ProductQueryServiceImpl -> findProductById()");
		Optional<Product> optionalProduct = productJpaRepository.findById(id);
		log.info("End - ProductQueryServiceImpl -> findProductById()");
		return optionalProduct.orElseThrow();
	}

}
