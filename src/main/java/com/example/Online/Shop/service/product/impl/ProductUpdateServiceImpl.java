package com.example.Online.Shop.service.product.impl;

import com.example.Online.Shop.repository.entities.Product;
import com.example.Online.Shop.repository.jpa.ProductJpaRepository;
import com.example.Online.Shop.service.product.ProductUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdateService {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product updateProduct(Long id, Product product) {
        log.info("Init - ProductUpdateServiceImpl -> updateProduct()");
        product.setId(id);
        // Con getReferenceById apunta hacia el producto pero no lo busca hasta que hace falta por lo que es más rapido que findById.
        Product requestProduct = productJpaRepository.getReferenceById(id);
        if (product.getId().equals(requestProduct.getId())){
            Product updateProduct = productJpaRepository.save(product);
            log.info("End - ProductUpdateServiceImpl -> updateProduct");
            return updateProduct;
        }
        return null;
    }
}
