package com.example.Online.Shop.service.product.factory;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.entities.Product;
import com.example.Online.Shop.util.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductFactory {

	private final ImageService imageService;

	// Construye un objeto del producto y el manejo de la imagen
	public Product buildProduct(String name, String description, int stock, String type, BigDecimal price,
			MultipartFile image) throws IOException {
		try {
			String imagePath = imageService.saveImageInDirectory(image); // Guarda la
																			// imagen en
																			// el
																			// directorio
																			// y obtiene
																			// su ruta
			return Product.builder()
				.name(name) // Asigna el nombre del producto
				.description(description) // Descripción del producto
				.stock(stock) // Stock inicial
				.type(type) // Tipo de producto
				.price(price.setScale(2, RoundingMode.HALF_UP).floatValue()) // Convierte
																				// el
																				// precio
																				// a float
																				// redondeando
																				// a 2
																				// decimales
				.image(imagePath) // Asigna la ruta de la imagen
				.build();
		}
		catch (IOException e) {
			log.error(String.valueOf(AppErrorCode.ERROR_BUILD_PRODUCT), e);
			throw new BusinessException(AppErrorCode.ERROR_BUILD_PRODUCT, e);
		}
	}

}
