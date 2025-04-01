package com.example.Online.Shop.controller;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.service.auth.AuthenticationService;
import com.example.Online.Shop.service.auth.CustomerTokenService;
import com.example.Online.Shop.service.auth.PasswordRecoveryService;
import com.onlineshop.apifirst.api.AuthApiDelegate;
import com.onlineshop.domain.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApiDelegate {

    private final AuthenticationService authenticationService;

    private final CustomerTokenService customerTokenService;

    private final ConversionService conversionService;

    private final PasswordRecoveryService passwordRecoveryService;

    // Obtiene el usuario autenticado actualmente
    @Override
    public ResponseEntity<CustomerResponse> getAuthenticatedUser() {
        log.info("INIT - AuthController -> getAuthenticatedUser()");
        CustomerResponse authenticatedCustomer = authenticationService.getAuthenticatedUser();
        log.info("END - AuthController -> getAuthenticatedUser()");
        return ResponseEntity.ok(authenticatedCustomer);
    }

    // Inicia el proceso de restablecimiento de contraseña
    @Override
    public ResponseEntity<EmailResponse> passwordReset(PasswordResetRequest passwordResetRequest) {
        log.info("INIT - AuthController -> passwordReset()");
        EmailResponse emailResponse = passwordRecoveryService.sendEmailToRecoveryPassword(passwordResetRequest);
        log.info("END - AuthController -> passwordReset()");
        return ResponseEntity.ok(emailResponse);
    }

    // Confirma el restablecimiento de contraseña
    @Override
    public ResponseEntity<CustomerResponse> passwordResetConfirm(PasswordResetConfirmRequest passwordResetConfirmRequest) {
        log.info("INIT - AuthController -> passwordResetConfirm()");
        Customer customer = passwordRecoveryService.confirmRecoveryPassword(passwordResetConfirmRequest);
        CustomerResponse customerResponse = conversionService.convert(customer, CustomerResponse.class);
        log.info("END - AuthController -> passwordResetConfirm()");
        return ResponseEntity.ok(customerResponse);
    }

    // Autentica a un usuario al loguearse
    @Override
    public ResponseEntity<AuthenticationResponse> loginUser(LoginRequest loginRequest) {
        log.info("INIT - AuthController -> loginUser()");
        Customer customer = conversionService.convert(loginRequest, Customer.class);
        log.info("END - AuthController -> loginUser()");
        return ResponseEntity.ok(authenticationService.authenticate(customer));
    }

    // Registra un nuevo usuario
    @Override
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody CustomerRequest customerRequest) {
        log.info("INIT - AuthController -> registerUser()");
        Customer customer = conversionService.convert(customerRequest, Customer.class);
        log.info("END - AuthController -> registerUser()");
        return ResponseEntity.ok(authenticationService.register(customer));
    }

    // Verifica a un usuario mediante token
    @Override
    public ResponseEntity<String> verifyUser(@PathVariable("token") String token) {
        log.info("INIT - AuthController -> verifyUser()");
        log.info("END - AuthController -> verifyUser()");
        return authenticationService.verifyUser(token);
    }

    //Refresca el token de autenticación usando el refreshtoken
    @Tag(name = "auth")
    @PostMapping(value = "/api/v1/auth/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Refreshes the authentication token using the refresh token.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Token refresh successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Authentication failed") })
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        log.info("INIT - AuthController -> refreshToken()");
        log.info("END - AuthController -> refreshToken()");
        return customerTokenService.refreshToken(request);
    }
}
