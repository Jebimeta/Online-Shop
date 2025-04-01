package com.example.Online.Shop.config;

import com.example.Online.Shop.config.properties.OnlineShopProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class StaticResourceConfig implements WebMvcConfigurer {

	private final OnlineShopProperties properties;

	@Override
	// Con este recurso manejamos imagenes cuando reciba peticiones de los productos o
	// imagenes
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/app/static/products/images/**", "/products/images/**")
			.addResourceLocations("file:/app/static/products/images/",
					properties.getUploadProperties().getLocalDirectory());
	}

}
