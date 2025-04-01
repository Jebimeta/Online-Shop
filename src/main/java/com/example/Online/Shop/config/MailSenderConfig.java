package com.example.Online.Shop.config;

import com.example.Online.Shop.config.properties.OnlineShopProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailSenderConfig {

	private final OnlineShopProperties properties;

	@Bean // Indica que este método produce un bean gestionado por el contenedor de Spring
	public JavaMailSender getJavaMailSender() {
		// Crea una nueva instancia de JavaMailSenderImpl (implementación de
		// JavaMailSender)
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// Configura el servidor SMTP (en este caso, el de Gmail)
		mailSender.setHost("smtp.gmail.com");

		// Establece el puerto SMTP (587 es el puerto común para TLS)
		mailSender.setPort(587);

		// Establece el nombre de usuario para la autenticación SMTP (obtenido de las
		// propiedades)
		mailSender.setUsername(properties.getMail().getUsername());

		// Establece la contraseña para la autenticación SMTP (obtenido de las
		// propiedades)
		mailSender.setPassword(properties.getMail().getPassword());

		// Obtiene las propiedades de configuración adicionales para JavaMail
		Properties props = mailSender.getJavaMailProperties();

		// Establece el protocolo de transporte como SMTP
		props.put("mail.transport.protocol", "smtp");

		// Habilita la autenticación SMTP
		props.put("mail.smtp.auth", "true");

		// Habilita STARTTLS para cifrar la conexión
		props.put("mail.smtp.starttls.enable", "true");

		// Activa el modo debug para ver logs detallados del proceso de envío
		props.put("mail.debug", "true");

		// Confía en el certificado SSL de smtp.gmail.com
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		// Establece timeout de conexión a 5000ms (5 segundos)
		props.put("mail.smtp.connectiontimeout", "5000");

		// Establece timeout general a 5000ms (5 segundos)
		props.put("mail.smtp.timeout", "5000");

		// Establece timeout de escritura a 5000ms (5 segundos)
		props.put("mail.smtp.writetimeout", "5000");

		// Devuelve el objeto JavaMailSender configurado
		return mailSender;
	}

}
