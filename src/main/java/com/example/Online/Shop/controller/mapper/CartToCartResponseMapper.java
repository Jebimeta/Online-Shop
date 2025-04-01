package com.example.Online.Shop.controller.mapper;

import com.example.Online.Shop.repository.entities.Cart;
import com.onlineshop.domain.vo.CartResponse;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring", uses = { CartDetailsToCartDetailsResponseMapper.class })
public interface CartToCartResponseMapper extends Converter<Cart, CartResponse> {

    @Mapping(source = "id", target = "userId")
    CartResponse convert(@NonNull Cart source);

}
