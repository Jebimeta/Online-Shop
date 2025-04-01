package com.example.Online.Shop.controller.mapper;

import com.example.Online.Shop.repository.entities.Customer;
import com.onlineshop.domain.vo.CustomerResponse;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerToCustomerResponseMapper extends Converter<Customer, CustomerResponse> {

	CustomerResponse convert(Customer source);

}
