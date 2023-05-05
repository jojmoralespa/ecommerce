package com.demo.ecommerce.user;

import com.demo.ecommerce.model.Order;
import com.demo.ecommerce.user.tokenRegistration.ConfirmationToken;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "user_table")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "order_user")
    @JsonIgnoreProperties("order_user")
    private List<Order> orderList;

    private Boolean enabled ;

    private Boolean locked ;

    // Authority is the Foreign Key to Authority entity
    // One user has several Authorities
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("userId")
    @ToString.Exclude
    private List<AuthorityPerUser> userPerAuthorityList;


    @OneToMany(mappedBy = "userid", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("userId")
    @OrderBy("id desc")
    @ToString.Exclude
    private List<ConfirmationToken> confirmationTokenList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (AuthorityPerUser authorityPerUser : userPerAuthorityList) {

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
    public boolean isAccountNonExpired() {return true;}
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}