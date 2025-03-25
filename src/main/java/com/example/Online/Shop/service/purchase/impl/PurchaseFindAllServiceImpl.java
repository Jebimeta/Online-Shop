package com.example.Online.Shop.service.purchase.impl;

import com.example.Online.Shop.repository.entities.Purchase;
import com.example.Online.Shop.repository.jpa.PurchaseJpaRepository;
import com.example.Online.Shop.service.purchase.PurchaseFindAllService;
import io.swagger.v3.core.util.ReferenceTypeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseFindAllServiceImpl implements PurchaseFindAllService {

    private final PurchaseJpaRepository purchaseJpaRepository;

    @Override
    public List<Purchase> findAllPurchases() {
        return purchaseJpaRepository.findAll();
    }

}
