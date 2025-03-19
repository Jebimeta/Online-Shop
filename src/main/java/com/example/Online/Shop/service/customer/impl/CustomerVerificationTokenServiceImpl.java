package com.example.Online.Shop.service.customer.impl;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.jpa.CustomerJpaRepository;
import com.example.Online.Shop.service.customer.CustomerVerificationTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerVerificationTokenServiceImpl implements CustomerVerificationTokenService {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    @Transactional
    public String generateVerificationToken(Customer customer) {
        return UUID.randomUUID().toString();
    }

    @Override
    @Transactional
    public Customer verifyCustomerByToken(String token) {
        Optional<Customer> verifyCustomer = customerJpaRepository.findByVerificationToken(token);
        if (verifyCustomer.isPresent()){
            Customer customer = verifyCustomer.get();
            customer.setStatus(true); // Actualizamos el estado del campo a true para indicar que el usuario ha sido verificado
            customer.setVerificationToken(null); // Vaciamos este campo, pues ya no nos sirve una vez el usuario ya ha sido verificado
            customerJpaRepository.save(customer);
            return customer;
        } else {
            throw new UsernameNotFoundException("Aquí va un businessException");
        }
    }

    @Override
    public Customer findCustomerByVerification(String token) {
        Optional<Customer> findCustomer = customerJpaRepository.findByVerificationToken(token);
        if (findCustomer.isPresent()){
            return findCustomer.get();
        } else {
            throw new UsernameNotFoundException("Aquí va otro businessException");
        }
    }
}
