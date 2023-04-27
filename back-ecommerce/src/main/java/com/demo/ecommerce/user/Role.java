package com.demo.ecommerce.user;

public enum Role {
    ADMIN,
    ROLE_USER




    //Si vas a usar hasrole o hasauthority solo usar un tipo que es GrantedAuthorities
    // la diferencia es que  cuando guardes el valor tendras el prefijo ROLE_ y el preAuthoraize
    // usar @PreAuthorize("hasRole('USER')") sin el prefijo
    // Luego @PreAuthorize("hasAuthority('ADMIN')") de esta manera distingo roles de authorities
    // para poder usar estas etiquetas debo poner la anotación @EnableMethodSecurity(prePostEnabled = true) en la clase de Configuración
}