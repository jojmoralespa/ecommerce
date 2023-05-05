package com.demo.ecommerce;

import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.pojo.RegisterRequest;
import com.demo.ecommerce.repository.CategoryRepository;
import com.demo.ecommerce.repository.ProductRepository;
import com.demo.ecommerce.user.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }


    //Code running before the app started and create in db 30 Categories
    @Bean
    CommandLineRunner run(CategoryRepository categoryRepository,
                          ProductRepository productRepository,
                          AuthorityRepository authorityRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
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



                //Create a user in memory
                if (userRepository.count() == 0) {

                    User superAdmin = User.builder()
                            .firstName("superAdmin")
                            .lastName("ecommerce")
                            .email("superadmin@gmail.com")
                            .password(passwordEncoder.encode("superAdmin"))
                            .userPerAuthorityList(new ArrayList<>())
                            .enabled(true)
                            .locked(false)
                            .build();

                    userRepository.save(superAdmin);

                    Authority authorityUser = new Authority(Role.USER);
                    Authority authorityAdmin = new Authority(Role.ADMIN);
                    authorityRepository.saveAll(List.of(authorityUser, authorityAdmin));

                    List<AuthorityPerUser> authorities = new ArrayList<>();
                    AuthorityPerUser user = new AuthorityPerUser();
                    AuthorityPerUser admin = new AuthorityPerUser();
                    user.setAuthorityId(authorityUser);
                    admin.setAuthorityId(authorityAdmin);
                    user.setUserId(superAdmin);
                    admin.setUserId(superAdmin);
                    authorities.add(user);
                    authorities.add(admin);

                    superAdmin.setUserPerAuthorityList(authorities);
                    userRepository.save(superAdmin);

                }

            }
        };
    }

}
