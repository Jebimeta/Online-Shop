package com.example.Online.Shop.controller.mapper;

import com.example.Online.Shop.repository.entities.Purchase;
import com.onlineshop.domain.vo.PurchaseResponse;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface PurchaseResponseToPurchaseMapper extends Converter<PurchaseResponse, Purchase> {

    Purchase convert(PurchaseResponse source);

}
