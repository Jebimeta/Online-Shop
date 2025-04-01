package com.example.Online.Shop.controller;

import com.example.Online.Shop.service.about.AboutService;
import com.onlineshop.apifirst.api.AboutApiDelegate;
import com.onlineshop.domain.vo.EmailRequest;
import com.onlineshop.domain.vo.EmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AboutController implements AboutApiDelegate {

    private final AboutService aboutService;

    @Override
    public ResponseEntity<EmailResponse> sendEmail(EmailRequest request){
        log.info("Init - AboutController -> sendEmail()");
        log.info("End - AboutController -> sendEmail()");
        return ResponseEntity.ok(aboutService.sendEmail(request));
    }
}
