package com.example.Online.Shop.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@AllArgsConstructor
public class OnlineShopSecurityProperties {

	private JwtProperties jwt;

}
