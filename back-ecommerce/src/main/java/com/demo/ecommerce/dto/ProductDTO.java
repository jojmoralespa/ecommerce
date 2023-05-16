package com.demo.ecommerce.dto;


import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductDTO {
    private int id;

    @NotNull(message = "The product NAME can't be null.")
    @NotBlank(message = "The product NAME can't be empty.")
    private String name;

    @NotNull(message = "The product PRICE can't be null.")
    @DecimalMin(value = "0.1", message = "The product PRICE must be at least 0.1.")
    private Double price;

    @NotNull(message = "The product description can't be null.")
    @NotBlank(message = "The product description can't be empty.")
    @Size(min = 10, max = 512, message = "The product description must be between 10 and 512 characters.")
    private String description;

    @NotNull(message = "The product imageURL can't be null.")
    @Pattern(regexp = "/\\.(jpg|jpeg|png|gif)$/i")
    private String imageUrl;

    @NotNull
    @JsonIgnoreProperties("productList")
    private Category category;

    // DTO Constructor setting Product object
    public ProductDTO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.imageUrl = product.getImageUrl();
        this.category = product.getCategory();
    }
}
