package com.example.Online.Shop.service.customer;

import com.example.Online.Shop.repository.entities.Customer;

public interface CustomerQueryService {

    Customer getCustomerByUsername(String username);

}
