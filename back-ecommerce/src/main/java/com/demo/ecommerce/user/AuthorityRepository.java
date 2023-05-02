package com.demo.ecommerce.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
//    Optional<Authority> findById(Integer id);
    Optional<Authority> findByName(String name);
}
