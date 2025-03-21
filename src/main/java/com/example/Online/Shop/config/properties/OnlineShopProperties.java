package com.example.Online.Shop.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@ConfigurationProperties(prefix = "onlineShop")
public class OnlineShopProperties {

	private final UploadProperties uploadProperties;

	private final OnlineShopSecurityProperties security;

	private final MailProperties mail;

}
