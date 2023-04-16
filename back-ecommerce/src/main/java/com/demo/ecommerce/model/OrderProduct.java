package com.demo.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "OrderPerProduct")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_order")
    @JsonIgnoreProperties("productOrderInterList")
    private Order orderId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_product")
    @JsonIgnoreProperties("orderProductInterList")
    private Product productId;
}
