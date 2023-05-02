package com.demo.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private Double price;
    private String description;
    private String imageUrl;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category")
    @JsonIgnoreProperties("productList")
    private Category category;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("productId")
    private List<OrderProduct> orderProductInterList;
}
