package com.demo.ecommerce.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//Verificar si solo era poner esto acá  y no lo de EAGER o en el userService el transaccional
//@Transactional(readOnly = true)
@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);




}
