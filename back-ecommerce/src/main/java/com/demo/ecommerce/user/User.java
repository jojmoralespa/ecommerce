package com.demo.ecommerce.user;

import com.demo.ecommerce.model.OrderProduct;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "user_table")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Enumerated(value = EnumType.STRING)
//    private Role role;

    // Authority is the Foreign Key to Authority entity
    // One user has several Authorities
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("userId")
    private List<AuthorityPerUser> authorityPerUserList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (AuthorityPerUser authorityPerUser : authorityPerUserList) {

            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authorityPerUser.getAuthorityId().getName().name());
            grantedAuthorities.add(simpleGrantedAuthority);
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}