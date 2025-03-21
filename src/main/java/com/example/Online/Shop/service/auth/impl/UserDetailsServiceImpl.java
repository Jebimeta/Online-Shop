package com.example.Online.Shop.service.auth.impl;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.jpa.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    // Carga los detalles de un usuario desde el repositoriopara poder utilizarloen el procueso de autenticación y autorización
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerJpaRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + customer.getRol().name()));

        return new User(customer.getUsername(), customer.getPassword(), authorities);
    }
}
