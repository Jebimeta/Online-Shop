package com.example.Online.Shop.controller;

import com.example.Online.Shop.repository.entities.Product;
import com.example.Online.Shop.service.product.*;
import com.example.Online.Shop.service.product.factory.ProductFactory;
import com.onlineshop.apifirst.api.ProductsApiDelegate;
import com.onlineshop.domain.vo.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductController implements ProductsApiDelegate {

    private final ConversionService conversionService;

    private final ProductsFindAllService productsFindAllService;

    private final ProductCreateService productCreateService;

    private final ProductQueryService productQueryService;

    private final ProductUpdateService productUpdateService;

    private final ProductDeleteService productDeleteService;

    private final ProductFactory productFactory;

    @Override
    public ResponseEntity<List<ProductResponse>> getProducts() {
        log.info("INIT - ProductController -> getProducts()");
        List<ProductResponse> productsResponse = productsFindAllService.findAllProducts()
                .stream()
                .map(product -> conversionService.convert(product, ProductResponse.class))
                .toList();
        log.info("END - ProductController -> getProducts()");
        return ResponseEntity.ok(productsResponse);
    }

    @Override
    public ResponseEntity<ProductResponse> createProduct(String name, String description, int stock, String type,
                                                         BigDecimal price, MultipartFile image) {
        log.info("INIT - ProductController -> createProduct()");
        try {
            Product product = productFactory.buildProduct(name, description, stock, type, price, image);
            Product createdProduct = productCreateService.createProduct(product);
            ProductResponse productResponse = conversionService.convert(createdProduct, ProductResponse.class);
            log.info("END - ProductController -> createProduct()");
            return ResponseEntity.ok(productResponse);
        }
        catch (IOException e) {
            log.info("EXCEPTION - ProductController -> createProduct()");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductResponse());
        }

    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(Long productId) {
        log.info("INIT - ProductController -> getProductById()");
        Product product = productQueryService.findProductById(productId);
        ProductResponse productResponse = conversionService.convert(product, ProductResponse.class);
        log.info("END - ProductController -> getProductById()");
        return ResponseEntity.ok(productResponse);
    }

    @Override
    public ResponseEntity<Void> deleteProductById(Long productId) {
        log.info("INIT - ProductController -> deleteProductById()");
        productDeleteService.deleteProductById(productId);
        log.info("END - ProductController -> deleteProductById()");
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(Long productId, String name, String description, int stock,
                                                         String type, BigDecimal price, MultipartFile image) {
        log.info("INIT - ProductController -> updateProduct()");
        try {
            Product product = productFactory.buildProduct(name, description, stock, type, price, image);
            Product updatedProduct = productUpdateService.updateProduct(productId, product);
            ProductResponse productResponse = conversionService.convert(updatedProduct, ProductResponse.class);
            log.info("END - ProductController -> updateProduct()");
            return ResponseEntity.ok(productResponse);
        }
        catch (IOException e) {
            log.info("EXCEPTION - ProductController -> updateProduct()");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductResponse());
        }

    }

}
