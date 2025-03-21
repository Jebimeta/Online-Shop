package com.example.Online.Shop.util;

import com.example.Online.Shop.config.properties.OnlineShopProperties;
import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
@Getter
@Setter
@AllArgsConstructor
public class ImageService {

	private OnlineShopProperties onlineShopProperties;

	// Método para subir una imagen
	public String saveImageInDirectory(MultipartFile image) throws IOException {
		log.info("Init - ImageService -> saveImageDirectory()");
		Path uploadDirectoryImage = Paths.get(onlineShopProperties.getUploadProperties().getDirectory());
		String imageName = getValidImageName(image);
		if (!Files.exists(uploadDirectoryImage)) {
			Files.createDirectories(uploadDirectoryImage);
		}
		saveImage(image, uploadDirectoryImage.resolve(imageName));
		log.info("End - The image was saved successfully");
		return imageName;
	}

	// Método para validar el nombre de la imagen
	private String getValidImageName(MultipartFile image) {
		String imageName = image.getOriginalFilename();
		if (StringUtils.isEmpty(image.getOriginalFilename())) {
			throw new BusinessException(AppErrorCode.ERROR_IMAGE_NAME);
		}
		return imageName;
	}

	// Método para guardar la imagen
	private void saveImage(MultipartFile image, Path destination) {
		try (InputStream inputStream = image.getInputStream()) {
			log.info("Init - ImageService -> saveImage() - Saving image");
			Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException exception) {
			log.error(" The image cannot be saved successfully: {}", destination.getFileName());
			throw new BusinessException(AppErrorCode.ERROR_SAVE_IMAGE, exception);
		}
	}

	// Método para borrar una imagen
	public void deleteImage(String imageName) {
		Path imagePath = Paths.get(onlineShopProperties.getUploadProperties().getDirectory(), imageName);
		try {
			Files.delete(imagePath);
		}
		catch (IOException exception) {
			log.error("The image cannot be deleted successfully: {}", imageName);
			throw new BusinessException(AppErrorCode.ERROR_DELETE_IMAGE);
		}
	}

}
