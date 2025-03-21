package com.example.Online.Shop.util;

import com.example.Online.Shop.config.properties.OnlineShopProperties;
import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.jpa.TokenJpaRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final OnlineShopProperties onlineShopProperties;

	private final TokenJpaRepository tokenJpaRepository;

	// Obtiene la clave secreta para firmar y verificar los tokens JWT
	private SecretKey getSigninKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(onlineShopProperties.getSecurity().getJwt().getSecretKey());
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Extrae todas las reclamaciones del token JWT
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
	}

	// Extrae una reclamación específica del token, como nombre de usuario o fecha de
	// expiración
	public <T> T extraClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	// Extrae la fecha de expiración
	private Date extractExpiration(String token) {
		return extraClaim(token, Claims::getExpiration);
	}

	// Extrae el nombre de usuario
	public String extractUsername(String token) {
		return extraClaim(token, Claims::getSubject);
	}

	// Verifica si el nombre del usuario en el token coincide con el proporcionado
	private boolean isTokenUsernameMatchingUser(String token, String username) {
		return extractUsername(token).equals(username);
	}

	// Verifica si el token JWT ha expirado
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Verifica si el token de acceso está activo(no ha cerrado sesión)
	private boolean isTokenActive(String token) {
		return tokenJpaRepository.findByAccessToken(token).map(t -> !t.isLoggedOut()).orElse(false);
	}

	// Verifica si el token de refresco está activo(no ha cerrado sesión)
	private boolean isRefreshTokenActive(String token) {
		return tokenJpaRepository.findByRefreshToken(token).map(t -> !t.isLoggedOut()).orElse(false);
	}

	// Verifica si un token JWT es valido para un usuario específico.
	public boolean isValid(String token, UserDetails user) {
		return isTokenUsernameMatchingUser(token, user.getUsername()) && !isTokenExpired(token) && isTokenActive(token);
	}

	// Verifica si un token de refresco es valido para un cliente especifico
	public boolean isValidRefreshToken(String token, Customer customer) {
		return isTokenUsernameMatchingUser(token, customer.getUsername()) && !isTokenExpired(token)
				&& isRefreshTokenActive(token);
	}

	// Genera un token JWT para un cliente por un tiempo de expiración especifico. Incluye
	// nombre, rol y fecha de expiración
	private String generateToken(Customer customer, String expirationInMillis) {
		long expireTimeInMillis = Long.parseLong(expirationInMillis);
		Date expirationDate = Date.from(Instant.now().plus(expireTimeInMillis, ChronoUnit.MILLIS));
		return Jwts.builder()
			.subject(customer.getUsername())
			.claim("role", customer.getRol().name())
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(expirationDate)
			.signWith(getSigninKey())
			.compact();
	}

	// Genera un token de acceso para un cliente
	public String generateAccessToken(Customer customer) {
		return generateToken(customer, onlineShopProperties.getSecurity().getJwt().getAccessTokenExpiration());
	}

	public String generateRefreshToken(Customer customer) {
		return generateToken(customer, onlineShopProperties.getSecurity().getJwt().getRefreshTokenExpiration());
	}

}
