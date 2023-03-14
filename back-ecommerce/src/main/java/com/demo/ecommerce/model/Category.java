package com.demo.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name")
    private String name;
    private String description;
    private String imageUrl;


    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category")
    private List<Product> productList;

}
