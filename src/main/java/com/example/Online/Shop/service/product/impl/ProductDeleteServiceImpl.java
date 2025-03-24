package com.example.Online.Shop.service.product.impl;

import com.example.Online.Shop.repository.entities.Product;
import com.example.Online.Shop.repository.jpa.ProductJpaRepository;
import com.example.Online.Shop.service.product.ProductDeleteService;
import com.example.Online.Shop.service.product.ProductQueryService;
import com.example.Online.Shop.util.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDeleteServiceImpl implements ProductDeleteService {

    private final ProductJpaRepository productJpaRepository;

    private final ImageService imageService;

    @Override
    public Void deleteProductById(Long id) {
        log.info("Init - ProductDeleteServiceImpl -> deleteProductById()");
        Optional<Product> product = productJpaRepository.findById(id);
        if (product.isPresent()){
            imageService.deleteImage(product.get().getImage());
            productJpaRepository.delete(product.get());
            log.info("End - ProductDeleteServiceImpl -> deleteProductById() - Product deleted");
        }
        return null;
    }
}
