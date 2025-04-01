package com.example.Online.Shop.service.purchase;

import com.example.Online.Shop.repository.entities.Purchase;
import com.onlineshop.domain.vo.PurchaseRequest;

public interface PurchaseCreateService {

	Purchase makePurchase(PurchaseRequest purchase);

}
