package com.example.Online.Shop.service.purchase;

import com.example.Online.Shop.repository.entities.Purchase;

public interface PurchaseCancellationService {

	Purchase cancelPurchaseById(Long id);

	Purchase cancelPurchaseConfirmationById(Long id);

}
