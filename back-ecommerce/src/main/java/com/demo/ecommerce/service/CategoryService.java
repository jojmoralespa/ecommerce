package com.demo.ecommerce.service;

import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepo;

    public Category save(Category category) {

        return categoryRepo.save(category);
    }

    public Category update(Category category) {

        //Get actual objet from DB
        Category categoryOpt = categoryRepo.findById(category.getId()).get();

        if (category.getName() != null)
            categoryOpt.setName(category.getName());

        if (category.getDescription() != null)
            categoryOpt.setDescription(category.getDescription());

        if (category.getImageUrl() != null)
            categoryOpt.setImageUrl(category.getImageUrl());


        //save the update object
        return categoryRepo.save(categoryOpt);

    }

    public Boolean existsById(Integer id) {
        return categoryRepo.existsById(id);
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepo.findById(id);
    }

    public Page<Category> findALl(Pageable page) {
        return categoryRepo.findAll(page);
    }

    public void deleteById(Integer id) {
        categoryRepo.deleteById(id);
    }
}
