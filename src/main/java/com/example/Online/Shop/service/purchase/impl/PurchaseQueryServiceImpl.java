package com.example.Online.Shop.service.purchase.impl;

import com.example.Online.Shop.exception.BusinessException;
import com.example.Online.Shop.exception.enums.AppErrorCode;
import com.example.Online.Shop.repository.entities.Purchase;
import com.example.Online.Shop.repository.jpa.PurchaseJpaRepository;
import com.example.Online.Shop.service.auth.AuthenticationService;
import com.example.Online.Shop.service.purchase.PurchaseQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseQueryServiceImpl implements PurchaseQueryService {

    private final PurchaseJpaRepository purchaseJpaRepository;

    private final AuthenticationService authenticationService;

    @Override
    @Transactional
    public Purchase findPurchaseById(Long purchasedId) {
        Purchase purchase = purchaseJpaRepository.findById(purchasedId)
                .orElseThrow(() -> new BusinessException(AppErrorCode.ERROR_CART_NOT_FOUND));
        return checkPurchaseInCustomerPurchase(purchase);
    }

    // Verifica si una compra pertenece a la compra de un cliente autenticado
    private Purchase checkPurchaseInCustomerPurchase(Purchase purchase){
        List<Purchase> customerPurchases = getCustomerPurchases();
        validatePurchaseInCustomerPurchases(purchase, customerPurchases);
        return purchase;
    }

    // Obtiene la compra del cliente autenticado
    private List<Purchase> getCustomerPurchases(){
        List<Purchase> customerPurchases = authenticationService.findUserByTokenAccess().getPurchases();
        return customerPurchases != null ? customerPurchases : Collections.emptyList();
    }

    // Valida que una compra esté en la lista de compra del cliente
    private void validatePurchaseInCustomerPurchases(Purchase purchase, List<Purchase> customerPurchase){
        if(!customerPurchase.contains(purchase)){
            log.error(AppErrorCode.ERROR_UNFORBIDDEN_PURCHASE.getMessage());
            throw new BusinessException(AppErrorCode.ERROR_UNFORBIDDEN_PURCHASE);
        }
    }
}
