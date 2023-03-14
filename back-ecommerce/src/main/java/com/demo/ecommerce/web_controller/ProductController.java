package com.demo.ecommerce.web_controller;

import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProductController {
    //Instancia de Logger
    private final Logger log = LoggerFactory.getLogger(Product.class);


    @Autowired
    private ProductService productService;

    @GetMapping("/product/list")
    public List<Product> findAll() {
        return productService.findALl();
    }

    @PostMapping("/product/create")
    public ResponseEntity<Product> createCategory(@RequestBody Product product){

        if (product.getId() != null) {
            log.warn("Trying to create a category with an Id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productService.save(product));

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findOneById(@PathVariable Integer id) {

        if (!productService.existsById(id)) {
            log.warn("Trying to find one Category with a non existing Id");
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(productService.findById(id).get());
        }
    }

    @PutMapping("/product/update")
    public ResponseEntity<Product> update(@RequestBody Product product) {

        if (product.getId() == null) {
            log.warn("Trying to update a category without an Id");
            return ResponseEntity.badRequest().build();
        }

        if (!productService.existsById(product.getId())) {
            log.warn("Trying to update a non existing category");
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(productService.update(product));

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> delete(@PathVariable Integer id) {

        if (!productService.existsById(id)) {
            log.warn("Trying to delete a non existing category");
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}
