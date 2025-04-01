package com.example.Online.Shop.controller.mapper;

import com.example.Online.Shop.repository.entities.CartDetails;
import com.onlineshop.domain.vo.CartDetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CartDetailsToCartDetailsResponseMapper extends Converter<CartDetails, CartDetailsResponse> {

	@Mapping(source = "product.id", target = "productId")
	CartDetailsResponse convert(CartDetails source);

}
