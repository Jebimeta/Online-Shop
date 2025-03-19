package com.example.Online.Shop.service.customer.impl;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.jpa.CustomerJpaRepository;
import com.example.Online.Shop.service.customer.CustomerDeleteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerDeleteServiceImpl implements CustomerDeleteService {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    @Transactional
    public Void deleteCustomerByUsername(String username) {
        Optional<Customer> customerEntity = customerJpaRepository.findByUsername(username);

        if(customerEntity.isPresent()){
            Customer customer = customerEntity.get();
            customer.setStatus(false); // En vez de eliminar el usuario, cambiamos su status a false y lo marca como usuario inactivo
            customerJpaRepository.save(customer);
            log.info("User {} has been deleted", username);
            return null;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
