package com.demo.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ORDER_TABLE")
public class Order {

    // Attributes
    // id is the Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;

    // user is the Foreign Key to User entity
    //private Integer user;

    // product is the Foreign Key to Product entity
    // One product has several orders
    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("orderId")
    private List<OrderProduct> productOrderInterList;


}
