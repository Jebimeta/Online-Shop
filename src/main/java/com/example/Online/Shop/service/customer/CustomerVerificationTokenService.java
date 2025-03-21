package com.example.Online.Shop.service.customer;

import com.example.Online.Shop.repository.entities.Customer;

public interface CustomerVerificationTokenService {

	String generateVerificationToken(Customer customer);

	Customer verifyCustomerByToken(String token);

	Customer findCustomerByVerification(String token);

}
