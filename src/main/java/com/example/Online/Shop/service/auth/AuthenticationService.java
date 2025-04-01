package com.example.Online.Shop.service.auth;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.jpa.CustomerJpaRepository;
import com.example.Online.Shop.service.customer.CustomerQueryService;
import com.example.Online.Shop.service.customer.CustomerVerificationTokenService;
import com.example.Online.Shop.service.customer.factory.CustomerRequestFactory;
import com.example.Online.Shop.util.JwtService;
import com.example.Online.Shop.util.MailSenderService;
import com.onlineshop.domain.vo.AuthenticationResponse;
import com.onlineshop.domain.vo.CustomerResponse;
import com.onlineshop.domain.vo.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

	private final CustomerJpaRepository repository;

	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	private final CustomerVerificationTokenService tokenService;

	private final MailSenderService mailSenderService;

	private final CustomerTokenService customerTokenService;

	private final ConversionService conversionService;

	private final CustomerQueryService customerQueryService;

	private final CustomerRequestFactory customerRequestFactory;

	// Autentica al usuario utilizando su correo y contraseña y generar tokens de acceso y
	// refresco
	public AuthenticationResponse authenticate(Customer loginRequest) {
		log.info("Init - AuthenticationService -> authenticate()");
		authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		Customer user = repository.findByEmail(loginRequest.getEmail()).orElseThrow();

		if (Boolean.FALSE.equals(user.getStatus())) {
			throw new BusinessException(AppErrorCode.ERROR_NOT_VERIFIED_ACCOUNT);
		}

		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		customerTokenService.revokeAllTokenByUser(user);
		customerTokenService.saveUserToken(accessToken, refreshToken, user);

		log.info("End - AuthenticationService -> authenticate()");
		return new AuthenticationResponse(accessToken, refreshToken, "User login was successfully");
	}

	// Registra un nuevo usuario en el sistema, verifica si ya existe, genera un token de
	// verificacion y envia un correo para verificar la cuenta
	public RegisterResponse register(Customer request) {
		if (repository.findByEmail(request.getEmail()).isPresent()) {
			log.info("User already exists in database");
			return new RegisterResponse(null, null, "User already exists in database");
		}

		log.info("Init - AuthenticationService -> register()");

		Customer user = customerRequestFactory.createCustomerRequest(request);
		String verificationToken = tokenService.generateVerificationToken(user);
		user.setVerificationToken(verificationToken);
		RegisterResponse response = sendVerificationEmail(user);
		log.info("End - AuthenticationService -> register()");
		return response;
	}

	// Envia el correo de verificación al usuario
	private RegisterResponse sendVerificationEmail(Customer user) {
		try {
			log.info("Attempting to register user: {}", user.getEmail());
			repository.save(user);
			mailSenderService.sendVerificationEmail(user);
		}
		catch (BusinessException e) {
			log.error("Error sendind verification email", e);
			repository.delete(user);
			return new RegisterResponse(null, null, "Failed to send verification email. Please try again.");
		}

		return new RegisterResponse(null, null,
				"User registered successfully. Please check your email to verify your account.");
	}

	// Verifica al usuario cuando clicka en el enlace del correo
	public ResponseEntity<String> verifyUser(String token) {
		try {
			log.info("Init - AuthenticationService -> verifyUser");

			Customer customer = tokenService.verifyCustomerByToken(token);
			customer.setStatus(true);
			customer.setVerificationToken(null);
			repository.save(customer);

			log.info("End - AuthenticationService -> verifyUser() - {} has been successfully verified",
					customer.getEmail());

			return new ResponseEntity<>("Account verified successfully.", HttpStatus.OK);
		}
		catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// Obtiene la información del usuario autenticado actualmente en el sistema.
	public CustomerResponse getAuthenticatedUser() {
		String username = getUsernameFromPrincipal(
				SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		log.info("AuthenticatedUser: {}", username);
		Customer findedCustomer = repository.findByUsername(username)
			.orElseThrow(() -> new RuntimeException("User not found"));

		return conversionService.convert(findedCustomer, CustomerResponse.class);
	}

	// Extrae el nombre de usuario del objeto principal
	private String getUsernameFromPrincipal(Object principal) {
		return (principal instanceof UserDetails userDetails) ? userDetails.getUsername()
				: throwuserNotAuthenticatedException();
	}

	// Lanza una excepción cuando el usuario no está autenticado
	private String throwuserNotAuthenticatedException() {
		throw new BusinessException(AppErrorCode.ERROR_NOT_AUTHENTICATED);
	}

	public Customer findUserByTokenAccess() {
		CustomerResponse customerResponse = getAuthenticatedUser();
		return customerQueryService.getCustomerByUsername(customerResponse.getUsername());
	}

}
