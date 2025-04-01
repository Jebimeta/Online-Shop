package com.example.Online.Shop.util;

import com.example.Online.Shop.config.properties.OnlineShopProperties;
import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.service.about.EmailResponseFactory;
import com.example.Online.Shop.service.customer.CustomerQueryService;
import com.example.Online.Shop.service.customer.CustomerUpdateService;
import com.example.Online.Shop.service.customer.CustomerVerificationTokenService;
import com.onlineshop.domain.vo.EmailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Log4j2
@Service
@RequiredArgsConstructor
public class MailSenderService {

	private final JavaMailSender mailSender;

	private final OnlineShopProperties properties;

	private final CustomerQueryService queryService;

	private final CustomerVerificationTokenService tokenService;

	private final CustomerUpdateService updateService;

	// Envía un correo electrónico de verificación al usuario
	public void sendVerificationEmail(Customer user) throws BusinessException {
		String subject = "Verificación de Registro";
		String senderName = "Online-Shop";
		String verifyUrl = properties.getMail().getHost() + "/auth/verify/" + user.getVerificationToken();

		String content = "Estimado " + user.getName() + ", <br>"
				+ "Por favor, haga clic en el siguiente enlace para verificar su registro:<br>" + "<h3><a href=\""
				+ verifyUrl + "\" target=\"_self\">VERIFICAR</a></h3>" + "Gracias,<br>" + senderName + ".";

		try {
			sendMessage(user.getEmail(), senderName, subject, content);
		}
		catch (MessagingException | UnsupportedEncodingException e) {
			log.error(e);
			throw new BusinessException(AppErrorCode.ERROR_SEND_VERIFICATION_EMAIL);
		}
	}

	// Envia correo al propietario de la web del formulario Contacta con nosotros
	public EmailResponse receiveContactUs(String senderName, String phoneNumber, String gender, String emailMessage)
			throws BusinessException {
		String subject = "Contacta con nosotros";
		String email = properties.getMail().getHostEmail();
		String headerMessage = "Se ha recibido un correo a través del formulario de contacta con nosotros, "
				+ "la información es la siguiente: ";
		String content = headerMessage + "<br><br>" + "Usurario: " + senderName + "<br>" + "Número de teléfono: "
				+ phoneNumber + "<br>" + "<br>" + "Mensaje " + emailMessage;

		try {
			sendMessage(email, senderName, subject, content);
			return EmailResponseFactory.createEmailResponse("The email was sent successfully");
		}
		catch (MessagingException | UnsupportedEncodingException e) {
			log.error(e);
			throw new BusinessException(AppErrorCode.ERROR_SEND_EMAIL);
		}

	}

	// Envía correo para recuperar clave
	public EmailResponse sendPasswoerdRecoveryEmail(String email) {
		String subject = "Restablecer contraseña";
		String senderName = "Online-Shop";

		Customer customer = queryService.getCustomerByUsername(email);
		String verificationToken = tokenService.generateVerificationToken(customer);
		customer.setVerificationToken(verificationToken);

		String verifyURL = properties.getMail().getHost() + "/auth/password-reset/request/"
				+ customer.getVerificationToken();

		String content = "Estimado " + customer.getName() + ",<br>"
				+ "Por favor, haga clic en el siguiente enlace para asignar una nueva contraseña:<br>"
				+ "<h3><a href=\"" + verifyURL + "\" target=\"_self\">CAMBIAR CONTRASEÑA</a></h3>" + "Gracias,<br>"
				+ senderName + ".";

		try {
			sendMessage(customer.getEmail(), senderName, subject, content);
			updateService.updateCustomer(customer);
		}
		catch (MessagingException | UnsupportedEncodingException e) {
			log.error(e);
			throw new BusinessException(AppErrorCode.ERROR_SEND_VERIFICATION_EMAIL);
		}

		return new EmailResponse("Se ha creado el correo correctamente");
	}

	// Envía el correo
	private void sendMessage(String email, String senderName, String subject, String content)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(properties.getMail().getUsername(), senderName);
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);
	}

}
