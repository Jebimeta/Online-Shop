package com.example.Online.Shop.repository.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mapstruct.EnumMapping;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime purchaseDate;

    @Column(name = "total_amount", nullable = false)
    private Float totalAmount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum  status;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @OneToMany(mappedBy = "purchase")
    private List<PurchaseDetails> purchaseDetails;

}
