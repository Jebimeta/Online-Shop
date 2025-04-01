package com.example.Online.Shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Aplica la configuración CORS a todas las
											// rutas
					.allowedOrigins("*") // Permite solicitudes desde cualquier origen
					.allowedMethods("*") // Permite todos los métodos de HTTP(GET, PUT...)
					.allowedHeaders("*") // Permite todos los headers en las solicitudes
					.allowCredentials(false) // No permite el envio de
												// credenciales(cookies, auth HTTP)
					.maxAge(3600); // Cachea la configuración CORS por 3600 segundos(1
									// hora)
			}
		};
	}

}
