package com.example.Online.Shop.filter;

import com.example.Online.Shop.service.auth.impl.UserDetailsServiceImpl;
import com.example.Online.Shop.util.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// Filtro de autenticación JWT que se ejecuta una vez por cada solicitud HTTP
// Verifica y valida tokens JWT en el encabezado "Authorization"
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	// Nombre del encabezado HTTP que contiene el token
	private static final String AUTH_HEADER = "Authorization";

	// Prefijo del token JWT
	private static final String BEARER_PREFIX = "Bearer ";

	private final JwtService jwtService;

	private final UserDetailsServiceImpl userDetailsService;

	// Método principal que procesa cada solicitud HTTP
	// Extrae el token JWT, lo valida y establece la autenticación en el contexto de
	// seguridad
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Extrae el token del encabezado
		String token = extractToken(request.getHeader(AUTH_HEADER));

		// Si hay token y no hay una autenticación previa, autentica al usuario
		if (token != null && !isAuthenticationPresent()) {
			authenticationUser(token, request);
		}

		// Continua con la cadena de filtros
		filterChain.doFilter(request, response);
	}

	// Extrae el token del encabezado.
	private String extractToken(String authHeader) {
		if (isBearerToken(authHeader)) {
			return authHeader.substring(BEARER_PREFIX.length()); // Elimina "Bearer " para
																	// obtener el token
																	// puro
		}
		return null;
	}

	// Verifica si el encabezado de autorización contiene un token Bearer
	private boolean isBearerToken(String authHeader) {
		return authHeader != null && authHeader.startsWith(BEARER_PREFIX);
	}

	// Verifica si ya existe una autenticación en el contexto de seguridad
	private boolean isAuthenticationPresent() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}

	// Autentica al usuario basado en el token JWT
	private void authenticationUser(String token, HttpServletRequest request) {
		// Extra el nombre del usuario del token
		String extractedUsername = jwtService.extractUsername(token);

		// Si el token contiene un username y es valido para ese usuario, establece la
		// autenticacion en el contexto de spring security
		if (extractedUsername != null
				&& jwtService.isValid(token, userDetailsService.loadUserByUsername(extractedUsername))) {
			setAuthentication(userDetailsService.loadUserByUsername(extractedUsername), request);
		}
	}

	// Establece la autenticacion en el contexto de seguridad de Spring
	private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
		// Crea un token de autenticacion Spring con usuario cedenciales y roles/permisos
		// de usuario
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

		// Añade detalles de la solicitud(Ip, agente de usuario, etc.)
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		// Guarda la autenticación en el contexto de seguridad
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

}
