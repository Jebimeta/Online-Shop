package com.example.Online.Shop.service.about;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.util.MailSenderService;
import com.onlineshop.domain.vo.EmailRequest;
import com.onlineshop.domain.vo.EmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;

@Service
@Slf4j
@RequiredArgsConstructor
public class aboutService {

    private final MailSenderService mailSenderService;

    // Maneja el envío de correos relacionado con el formulario de "Contáctanos"
    public EmailResponse sendEmail(EmailRequest request){
        try {
            log.info("Sending contact us email");
            return mailSenderService.receiveContactUs(request.getSenderName(), request.getPhoneNumber(), request.getGender(), request.getEmailMessage());
        } catch (BusinessException exception){
            log.error(String.valueOf(AppErrorCode.ERROR_SEND_EMAIL), exception.toString());
            return EmailResponseFactory.createEmailResponse(AppErrorCode.ERROR_SEND_EMAIL.getMessage());
        }
    }
}
