package com.example.Online.Shop.service.customer.impl;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.jpa.CustomerJpaRepository;
import com.example.Online.Shop.service.customer.CustomerUpdateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerUpdateServiceImpl implements CustomerUpdateService {

	private final CustomerJpaRepository customerJpaRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public Customer updateCustomer(Customer customerUpdateRequest) {
		Optional<Customer> updateCustomer = customerJpaRepository.findByEmail(customerUpdateRequest.getEmail());
		if (updateCustomer.isPresent()) {
			Customer findedCustomer = updateCustomer.get();
			findedCustomer.setName(customerUpdateRequest.getName());
			findedCustomer.setPassword(customerUpdateRequest.getPassword());
			findedCustomer.setSurname(customerUpdateRequest.getSurname());
			findedCustomer.setSurname2(customerUpdateRequest.getSurname2());
			findedCustomer.setAddress(customerUpdateRequest.getAddress());
			findedCustomer.setProvince(customerUpdateRequest.getProvince());
			findedCustomer.setRegion(customerUpdateRequest.getRegion());
			findedCustomer.setEmail(customerUpdateRequest.getEmail());
			findedCustomer.setPhone(customerUpdateRequest.getPhone());
			findedCustomer.setStatus(customerUpdateRequest.getStatus());
			findedCustomer.setRol(findedCustomer.getRol());
			findedCustomer.setPurchases(findedCustomer.getPurchases());
			findedCustomer.setTokens(findedCustomer.getTokens());
			return customerJpaRepository.save(findedCustomer);
		}
		else {
			throw new UsernameNotFoundException("Aquí va un AppErrorCode");
		}

	}

}
