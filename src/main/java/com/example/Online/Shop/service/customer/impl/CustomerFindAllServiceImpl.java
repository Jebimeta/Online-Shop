package com.example.Online.Shop.service.customer.impl;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.jpa.CustomerJpaRepository;
import com.example.Online.Shop.service.customer.CustomerFindAllService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerFindAllServiceImpl implements CustomerFindAllService {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    @Transactional
    public List<Customer> findAllCustomers() {
        log.info("Init - CustomerFindAllServiceImpl -> findAllCustomers");
        List<Customer> foundCustomers = customerJpaRepository.findAll();
        log.info("End - CustomerFindAllServiceImpl -> findAllCustomers");
        return foundCustomers;
    }
}
