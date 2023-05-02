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


                //Create some Authorities when the application start in order to be able to perform
                //the relation with user
                if (authorityRepository.count() == 0) {
                    authorityRepository.saveAll(List.of(
                            new Authority(Role.USER),
                            new Authority(Role.ADMIN)
                    ));
                }



                //Create a user in memory
                if (userRepository.count() == 0) {
                    List<AuthorityPerUser> authorities = new ArrayList<>();
                    AuthorityPerUser user = new AuthorityPerUser();
                    AuthorityPerUser admin = new AuthorityPerUser();
                    user.setAuthorityId(authorityRepository.findById(1).get());
                    admin.setAuthorityId(authorityRepository.findById(2).get());
                    authorities.add(user);
                    authorities.add(admin);

                    User superAdmin = User.builder()
                            .firstName("superAdmin")
                            .lastName("ecommerce")
                            .email("superadmin@gmail.com")
                            .password(passwordEncoder.encode("superAdmin"))
                            .authorityPerUserList(authorities)
                            .build();

                    for(AuthorityPerUser authority: superAdmin.getAuthorityPerUserList()){
                        authority.setUserId(superAdmin);
                    }
                    //userRepository.save(superAdmin);
                    /*
                    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                    SimpleGrantedAuthority user = new SimpleGrantedAuthority(Role.USER.name());
                    SimpleGrantedAuthority admin = new SimpleGrantedAuthority(Role.ADMIN.name());
                    authorities.add(user);
                    authorities.add(admin);

                    User superAdmin = new User("superadming@gmail.com",
                            passwordEncoder.encode("superAdmin"),
                            authorities);
                    userRepository.save(superAdmin);
                    */
                }

            }
        };
    }

}
