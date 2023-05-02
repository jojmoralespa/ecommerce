package com.demo.ecommerce.web_controller;

import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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

    //allow use pageable object for pages, size and sort params

    @GetMapping("/category/list")
    // If "hasRole('NAME') -> Actual name = 'ROLE_USER'
    @PreAuthorize("hasAuthority('USER')")
    public Page<Category> findAll(@PageableDefault(size = 5) Pageable page) {
        return categoryService.findALl(page);
    }


    @PostMapping("/category/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){

        if (category.getId() != null) {
            log.warn("Trying to create a category with an Id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(categoryService.save(category));

    }


    @GetMapping("/category/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
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
