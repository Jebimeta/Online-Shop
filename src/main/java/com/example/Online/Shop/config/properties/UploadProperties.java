package com.example.Online.Shop.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UploadProperties {

	private String directory;

	private String baseUrl;

	private String localDirectory;

}
