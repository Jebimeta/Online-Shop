package com.example.Online.Shop.controller.mapper;

import com.example.Online.Shop.repository.entities.Customer;
import com.onlineshop.domain.vo.CustomerRequest;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerRequestToCustomerMapper extends Converter<CustomerRequest, Customer> {

	Customer convert(@NonNull CustomerRequest source);

}