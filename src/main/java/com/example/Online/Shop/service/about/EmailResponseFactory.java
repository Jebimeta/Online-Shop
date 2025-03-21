package com.example.Online.Shop.service.about;

import com.onlineshop.domain.vo.EmailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailResponseFactory {

    private EmailResponseFactory() {
        log.info("You cannot use EmailResponseFactroy constructor because is private");
    }

    public static EmailResponse createEmailResponse(String message){
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setMessage(message);
        return emailResponse;
    }
}
