package com.example.Online.Shop.service.purchase.impl;

import com.example.Online.Shop.repository.entities.Purchase;
import com.example.Online.Shop.repository.enums.StatusEnum;
import com.example.Online.Shop.service.purchase.PurchaseCancellationService;
import com.example.Online.Shop.service.purchase.PurchaseQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseCancellationServiceImpl implements PurchaseCancellationService {

	private final PurchaseQueryService purchaseQueryService;

	@Override
	@Transactional
	// Cancela la compra
	public Purchase cancelPurchaseById(Long id) {
		log.info("Init - PurchaseCancellationServiceImpl -> cancelPurchaseById()");
		Purchase obtainedPurchase = purchaseQueryService.findPurchaseById(id);
		changeStatus(obtainedPurchase, StatusEnum.CANCELLATION_PENDING);
		log.info("End - PurchaseCancellationServiceImpl -> cancelPurchaseById");
		return obtainedPurchase;
	}

	@Override
	// Confirma la cancelación de una compra cambiando su estado a CANCELLED
	public Purchase cancelPurchaseConfirmationById(Long id) {
		log.info("Init - PurchaseCancellationServiceImpl -> cancelPurchaseConfirmationById()");
		Purchase obtainedPurchase = purchaseQueryService.findPurchaseById(id);
		changeStatus(obtainedPurchase, StatusEnum.CANCELLED);
		log.info("End - PurchaseCancellationServiceImpl -> cancelPurchaseConfirmationById");
		return obtainedPurchase;
	}

	// Cambia el estado de la compra
	private void changeStatus(Purchase purchase, StatusEnum status) {
		log.info("Init - PurchaseCancellationServiceImpl -> changeStatus()");
		purchase.setStatus(status);
		log.info("End - PurchaseCancellationServiceImpl -> changeStatus");
	}

}