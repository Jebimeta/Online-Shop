package com.example.Online.Shop.service.auth;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.entities.Token;
import com.example.Online.Shop.repository.jpa.CustomerJpaRepository;
import com.example.Online.Shop.repository.jpa.TokenJpaRepository;
import com.example.Online.Shop.util.JwtService;
import com.onlineshop.domain.vo.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerTokenService {

    private final TokenJpaRepository tokenJpaRepository;

    private final JwtService jwtService;

    private final CustomerJpaRepository customerJpaRepository;

    // Revoca todos los tokens de acceso de un usuario asegurando que no puedan ser utilizados nuevamente para autenticación
    public void revokeAllTokenByUser(Customer user){
        List<Token> validTokens = tokenJpaRepository.findAllAccessTokenByUser(user.getId());
        if (validTokens.isEmpty()){
            return;
        }
        validTokens.forEach(t -> t.setLoggedOut(true));
        tokenJpaRepository.saveAll(validTokens);
    }

    // Guarda un nuevo token de acceso y un token de actualización
    public void saveUserToken(String accessToken, String refreshToken, Customer user){
        Token token = new Token();
        token.setAccesToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setCustomer(user);
        tokenJpaRepository.save(token);
    }

    // Obtiene un usuario a partir de un token de actualización
    public Customer findUserByRefreshToken(String refreshToken){
        String username = jwtService.extractUsername(refreshToken);
        return customerJpaRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Genera nuevos tokens de acceso y actualización para un usuario
    public AuthenticationResponse createNewTokens(Customer user){
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(newAccessToken, newRefreshToken, user);
        return new AuthenticationResponse(newAccessToken, newRefreshToken, "New token generated");
    }

    // Valida un refreshToken y si es válido, generar nuevos accessToken y actualización para el usuario
    public ResponseEntity<AuthenticationResponse> validateAndGenerateNewTokens(String refreshToken, Customer user){
        if(!jwtService.isValidRefreshToken(refreshToken, user)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        AuthenticationResponse response = createNewTokens(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Maneja la solicitud de renovación de tokens
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request){
        String autHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken = autHeader.substring(7);
        if (autHeader.startsWith("Bearer ")){
            Customer user = findUserByRefreshToken(refreshToken);
            return validateAndGenerateNewTokens(refreshToken, user);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
