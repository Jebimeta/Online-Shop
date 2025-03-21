package com.example.Online.Shop.service.customer.impl;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.jpa.CustomerJpaRepository;
import com.example.Online.Shop.service.customer.CustomerQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {

	private final CustomerJpaRepository customerJpaRepository;

	@Override
	@Transactional
	public Customer getCustomerByUsername(String username) {
		Optional<Customer> customerEntity = customerJpaRepository.findByUsername(username);
		if (customerEntity.isPresent()) {
			return customerEntity.get();
		}
		else {
			throw new UsernameNotFoundException("User not Found");
		}
	}

}
