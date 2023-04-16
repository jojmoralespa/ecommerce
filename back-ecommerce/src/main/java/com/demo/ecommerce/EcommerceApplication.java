package com.demo.ecommerce;

import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repository.CategoryRepository;
import com.demo.ecommerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }


    //Code running before the app started and create in db 30 Categories
@Bean
    CommandLineRunner run(CategoryRepository categoryRepository, ProductRepository productRepository) {
    return new CommandLineRunner() {
        @Override
        public void run(String... args) throws Exception {

            IntStream.rangeClosed(1, 10).forEach(i -> {
                Category category = new Category();
                category.setName("Category" + i);
                category.setDescription("Description" + i);
                category.setImageUrl("URL" + i);

                categoryRepository.save(category);
            });

            IntStream.rangeClosed(1, 10).forEach(i -> {
                Product product = new Product();
                product.setName("Product" + i);
                product.setDescription("Description" + i);
                product.setImageUrl("URL" + i);
                product.setPrice(1.0 * i);
                product.setCategory(categoryRepository.findById(1).get());

                productRepository.save(product);
            });



        }
    };
}

}
