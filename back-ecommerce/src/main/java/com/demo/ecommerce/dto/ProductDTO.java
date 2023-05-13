package com.demo.ecommerce.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductDTO {
    @NotNull(message = "The product NAME can't be null.")
    @NotBlank(message = "The product NAME can't be empty.")
    private String name;

    @NotNull(message = "The product PRICE can't be null.")
    @DecimalMin(value = "0.1", message = "The product PRICE must be at least 0.1.")
    private double price;

    @NotNull(message = "The product description can't be null.")
    @NotBlank(message = "The product description can't be empty.")
    @Size(min = 10, max = 512, message = "The product description must be between 10 and 512 characters.")
    private String description;

    @NotNull(message = "The product imageURL can't be null.")
    private String imageUrl;
}
