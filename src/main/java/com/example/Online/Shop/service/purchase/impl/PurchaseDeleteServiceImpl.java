package com.example.Online.Shop.service.purchase.impl;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.entities.Purchase;
import com.example.Online.Shop.repository.jpa.PurchaseJpaRepository;
import com.example.Online.Shop.service.purchase.PurchaseDeleteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseDeleteServiceImpl implements PurchaseDeleteService {

    private final PurchaseJpaRepository purchaseJpaRepository;

    @Override
    @Transactional
    public String deletePurchaseById(Long orderId) {
        Optional<Purchase> optionalPurchase = purchaseJpaRepository.findById(orderId);
        if (optionalPurchase.isPresent()){
            purchaseJpaRepository.delete(optionalPurchase.get());
            return "Purchase has been deleted successfully";
        }
        throw new BusinessException(AppErrorCode.ERROR_PURCHASE_NOT_FOUND);
    }
}
