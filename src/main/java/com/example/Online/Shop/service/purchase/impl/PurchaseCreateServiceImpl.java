package com.example.Online.Shop.service.purchase.impl;

import com.example.Online.Shop.repository.entities.*;
import com.example.Online.Shop.repository.jpa.PurchaseJpaRepository;
import com.example.Online.Shop.service.auth.AuthenticationService;
import com.example.Online.Shop.service.customer.CustomerQueryService;
import com.example.Online.Shop.service.purchase.PurchaseCreateService;
import com.example.Online.Shop.service.purchase.factory.PurchaseFactory;
import com.onlineshop.domain.vo.CustomerResponse;
import com.onlineshop.domain.vo.PurchaseRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseCreateServiceImpl implements PurchaseCreateService {

	private final PurchaseJpaRepository purchaseJpaRepository;

	private final CustomerQueryService customerQueryService;

	private final AuthenticationService authenticationService;

	@Override
	@Transactional
	// Procesa una nueva compra a partir de una solicitud de compra
	public Purchase makePurchase(PurchaseRequest purchaseRequest) {
		CustomerResponse customerResponse = authenticationService.getAuthenticatedUser();
		Customer customer = customerQueryService.getCustomerByUsername(customerResponse.getUsername());
		Cart cart = getCustomerLatestCart(customer);
		List<PurchaseDetails> purchaseDetailsList = createPurchaseDetailsList(cart);
		return savePurchase(purchaseRequest, customer, purchaseDetailsList);
	}

	// Obtiene el carrito más reciente del cliente
	private Cart getCustomerLatestCart(Customer customer) {
		return customer.getCart().get(customer.getCart().size() - 1);
	}

	// Convierte los items del carrito a detalles de compra
	private List<PurchaseDetails> createPurchaseDetailsList(Cart cart) {
		List<PurchaseDetails> purchaseDetailsList = new ArrayList<>();
		for (CartDetails cartDetails : cart.getCartDetails()) {
			PurchaseDetails purchaseDetails = new PurchaseDetails();
			purchaseDetails.setId(cartDetails.getId());
			purchaseDetails.setProduct(cartDetails.getProduct());
			purchaseDetails.setPrice(cartDetails.getPrice());
			purchaseDetails.setQuantity(cartDetails.getQuantity());
			purchaseDetailsList.add(purchaseDetails);
		}
		return purchaseDetailsList;
	}

	// Guarda una nueva compra en la base de datos
	private Purchase savePurchase(PurchaseRequest purchaseRequest, Customer customer,
			List<PurchaseDetails> purchaseDetailsList) {
		return purchaseJpaRepository
			.save(PurchaseFactory.createPurchaseFromPurchaseRequest(purchaseRequest, customer, purchaseDetailsList));
	}

}
