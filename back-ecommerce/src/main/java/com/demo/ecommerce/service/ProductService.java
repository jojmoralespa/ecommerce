package com.demo.ecommerce.service;

import com.demo.ecommerce.dto.ProductDTO;
import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repository.CategoryRepository;
import com.demo.ecommerce.repository.ProductRepository;
import com.demo.ecommerce.validators.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;

    @Autowired
    CategoryRepository categoryRepo;

    @Autowired
    ObjectValidator<ProductDTO> validator;

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductDTO save(ProductDTO productDTO) {
        // DTO validation
        validator.validate(productDTO);

        Product product = new Product(productDTO);
        Optional<Category> category = categoryRepo.findById(productDTO.getCategory().getId());
        if(category.isEmpty()){
            log.error("TRYING TO CREATE A PRODUCT OF AN NON EXISTENT CATEGORY {}", productDTO.getCategory().getId());
        }
        Category category1 = category.get();
        product.setCategory(category1);
        Product productComplete = productRepo.save(product);

        return new ProductDTO(productComplete);
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
