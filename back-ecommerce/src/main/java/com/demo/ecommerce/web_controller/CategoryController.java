package com.demo.ecommerce.web_controller;

import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class CategoryController {

    //Instancia de Logger
    private final Logger log = LoggerFactory.getLogger(Category.class);


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/list")
    public List<Category> findAll() {
        return categoryService.findALl();
    }

    @PostMapping("/category/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){

        if (category.getId() != null) {
            log.warn("Trying to create a category with an Id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(categoryService.save(category));

    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> findOneById(@PathVariable Integer id) {

        if (!categoryService.existsById(id)) {
            log.warn("Trying to find one Category with a non existing Id");
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(categoryService.findById(id).get());
        }
    }

    @PutMapping("/category/update")
    public ResponseEntity<Category> update(@RequestBody Category category) {

        if (category.getId() == null) {
            log.warn("Trying to update a category without an Id");
            return ResponseEntity.badRequest().build();
        }

        if (!categoryService.existsById(category.getId())) {
            log.warn("Trying to update a non existing category");
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(categoryService.update(category));

    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Category> delete(@PathVariable Integer id) {

        if (!categoryService.existsById(id)) {
            log.warn("Trying to delete a non existing category");
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();

    }


}