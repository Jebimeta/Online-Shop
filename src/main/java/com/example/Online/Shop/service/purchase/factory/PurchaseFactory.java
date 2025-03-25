package com.example.Online.Shop.service.purchase.factory;

import com.example.Online.Shop.repository.entities.Customer;
import com.example.Online.Shop.repository.entities.Purchase;
import com.example.Online.Shop.repository.entities.PurchaseDetails;
import com.example.Online.Shop.repository.enums.StatusEnum;
import com.onlineshop.domain.vo.PurchaseRequest;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseFactory {

    private PurchaseFactory(){
        // Constructor privado, no se deben de crear objetos vacíos desde esta clase.
    }

    // Crea un objeto Purchase a partir de una solicitud de compra con los detalles del cliente y los productos comprados
    public static Purchase createPurchaseFromPurchaseRequest(PurchaseRequest purchaseRequest, Customer customer, List<PurchaseDetails> purchaseDetailsList){
        return Purchase.builder()
                .customer(customer) // Cliente
                .status(StatusEnum.PLACED) // Establece el estado Placed
                .purchaseDate(LocalDateTime.now()) // Fecha de la compra
                .purchaseDetails(purchaseDetailsList) // Lista de los productos comprados
                .totalAmount(calculateToltalAmount(purchaseDetailsList)) // Calcula el monto final
                .shippingAddress(purchaseRequest.getShippingAddress()) // Dirección de envío
                .build();
    }

    // Calcula el monto total de la compra sumando el precio de cada item multiplicado por su cantidad.
    private static float calculateToltalAmount(List<PurchaseDetails> purchaseDetailsList){
        float totalAmount = 0;
        for(PurchaseDetails details : purchaseDetailsList){
            totalAmount += details.getPrice() * details.getQuantity();
        }
        return totalAmount;
    }
}
