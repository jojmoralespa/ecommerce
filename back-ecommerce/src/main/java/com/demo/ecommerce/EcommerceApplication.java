package com.demo.ecommerce;

import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.repository.CategoryRepository;
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
    CommandLineRunner run(CategoryRepository categoryRepository) {
    return new CommandLineRunner() {
        @Override
        public void run(String... args) throws Exception {

            IntStream.rangeClosed(1, 30).forEach(i -> {
                Category category = new Category();
                category.setName("category " + i);
                category.setDescription("descripcion " + i);
                category.setImageUrl("url" + i);

                categoryRepository.save(category);
            });

        }
    };
}

}
