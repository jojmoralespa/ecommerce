package com.demo.ecommerce.service;

import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repository.CategoryRepository;
import com.demo.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;

    public Product save(Product product) {

        return productRepo.save(product);
    }

    public Product update(Product product) {

        //Get actual objet from DB
        Product productOpt = productRepo.findById(product.getId()).get();

        if (product.getName() != null)
            productOpt.setName(product.getName());

        if (product.getDescription() != null)
            productOpt.setDescription(product.getDescription());

        if (product.getImageUrl() != null)
            productOpt.setImageUrl(product.getImageUrl());

        if (product.getPrice() != null)
            productOpt.setPrice(product.getPrice());

        //save the update object
        return productRepo.save(productOpt);

    }

    public Boolean existsById(Integer id) {
        return productRepo.existsById(id);
    }

    public Optional<Product> findById(Integer id) {
        return productRepo.findById(id);
    }

    public List<Product> findALl() {
        return productRepo.findAll();
    }

    public void deleteById(Integer id) {
        productRepo.deleteById(id);
    }
}
