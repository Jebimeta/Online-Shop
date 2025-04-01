package com.example.Online.Shop.service.purchase;

import com.example.Online.Shop.repository.entities.Purchase;

public interface PurchaseQueryService {

	Purchase findPurchaseById(Long orderId);

}
