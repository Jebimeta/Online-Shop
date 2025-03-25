package com.example.Online.Shop.service.purchase;

import com.example.Online.Shop.repository.entities.Purchase;

import java.util.List;

public interface PurchaseFindAllService {

    List<Purchase> findAllPurchases();

}
