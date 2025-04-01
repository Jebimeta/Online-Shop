package com.example.Online.Shop.config;

import com.example.Online.Shop.repository.jpa.TokenJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

	private static final String BEARER_PREFIX = "Bearer ";

	private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

	private final TokenJpaRepository tokenJpaRepository;

	// Método principal que se ejecuta durante el proceso de logout
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// 1. Extrae el token JWT del request
		String token = extractTokenFromRequest(request);

		// 2. Si existe el token, lo invalida
		if (token != null) {
			invalidateToken(token);
		}
		// Nota: Spring Security se encarga del resto de la respuesta
	}

	// Extrae el token JWT del encabezado Authorization
	private String extractTokenFromRequest(HttpServletRequest request) {
		// Obtiene el encabezado de autorización
		String authHeader = getAuthorizationHeader(request);

		// Verifica si es un token Bearer y lo extrae
		return isBearerToken(authHeader) ? extractBearerToken(authHeader) : null;
	}

	// Obtiene el encabezado Authorization de la solicitud HTTP
	private String getAuthorizationHeader(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}

	// Verifica si el encabezado contiene un token Bearer
	private boolean isBearerToken(String authHeader) {
		// El encabezado debe existir y comenzar con "Bearer "
		return authHeader != null && authHeader.startsWith(BEARER_PREFIX);
	}

	// Extrae el token eliminando el prefijo "Bearer "
	private String extractBearerToken(String authHeader) {
		return authHeader.substring(BEARER_PREFIX_LENGTH);
	}

	// Invalida el token marcándolo como "loggedOut" en la base de datos
	private void invalidateToken(String token) {
		// Busca el token en la base de datos
		tokenJpaRepository.findByAccessToken(token).ifPresent(storedToken -> {
			// Marca el token como invalidado (cierre de sesión)
			storedToken.setLoggedOut(true);

			// Guarda el cambio en la base de datos
			tokenJpaRepository.save(storedToken);
		});
	}

}
