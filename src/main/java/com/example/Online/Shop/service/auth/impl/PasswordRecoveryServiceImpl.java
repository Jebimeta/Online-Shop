package com.example.Online.Shop.service.auth.impl;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.service.auth.PasswordRecoveryService;
import com.example.Online.Shop.service.customer.CustomerUpdateService;
import com.example.Online.Shop.service.customer.CustomerVerificationTokenService;
import com.example.Online.Shop.util.MailSenderService;
import com.onlineshop.domain.vo.EmailResponse;
import com.onlineshop.domain.vo.PasswordResetConfirmRequest;
import com.onlineshop.domain.vo.PasswordResetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

	private final CustomerUpdateService customerUpdateService;

	private final MailSenderService mailSenderService;

	private final CustomerVerificationTokenService customerVerificationTokenService;

	@Override
	// Envia un correo para la recuperación de la contraseña
	public EmailResponse sendEmailToRecoveryPassword(PasswordResetRequest passwordResetRequest) {
		return mailSenderService.sendPasswoerdRecoveryEmail(passwordResetRequest.getEmail());
	}

	@Override
	// Confirma la recuperación de contraseña
	public Customer confirmRecoveryPassword(PasswordResetConfirmRequest passwordResetConfirmRequest) {
		Customer customer = customerVerificationTokenService
			.findCustomerByVerification(passwordResetConfirmRequest.getVerificationToken());
		customer.setPassword(passwordResetConfirmRequest.getNewPassword());
		customerUpdateService.updateCustomer(customer);

		return customer;
	}

}
