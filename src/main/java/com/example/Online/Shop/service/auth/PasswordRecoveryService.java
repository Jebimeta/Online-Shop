package com.example.Online.Shop.service.auth;

import com.example.Online.Shop.repository.entities.Customer;
import com.onlineshop.domain.vo.EmailResponse;
import com.onlineshop.domain.vo.PasswordResetConfirmRequest;
import com.onlineshop.domain.vo.PasswordResetRequest;

public interface PasswordRecoveryService {

    public EmailResponse sendEmailToRecoveryPassword(PasswordResetRequest passwordResetRequest);

    public Customer confirmRecoveryPassword(PasswordResetConfirmRequest passwordResetConfirmRequest);
}
