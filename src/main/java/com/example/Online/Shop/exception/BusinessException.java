package com.example.Online.Shop.exception;

import com.example.Online.Shop.exception.enums.AppErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private final AppErrorCode errorCode;

	public BusinessException(AppErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public BusinessException(AppErrorCode errorCode, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
	}

}
