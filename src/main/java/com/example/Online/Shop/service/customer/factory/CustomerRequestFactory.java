package com.example.Online.Shop.service.customer.factory;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.enums.RolEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerRequestFactory {

    private final PasswordEncoder passwordEncoder;

    public Customer createCustomerRequest(Customer requestCustomer){
        Customer user = new Customer();
        user.setName(requestCustomer.getName());
        user.setPassword(passwordEncoder.encode(requestCustomer.getPassword()));
        user.setUsername(requestCustomer.getEmail()); // Utilizamos el correo electronico como nombre de usuario
        user.setSurname(requestCustomer.getSurname());
        user.setSurname2(requestCustomer.getSurname2());
        user.setAddress(requestCustomer.getAddress());
        user.setProvince(requestCustomer.getProvince());
        user.setRegion(requestCustomer.getRegion());
        user.setEmail(requestCustomer.getEmail());
        user.setPhone(requestCustomer.getPhone());
        user.setStatus(false);
        user.setRol(RolEnum.USER);
        return user;
    }
}
