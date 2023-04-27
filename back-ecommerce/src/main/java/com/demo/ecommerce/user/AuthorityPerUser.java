package com.demo.ecommerce.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AuthorityPerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_authority")
    @JsonIgnoreProperties("userPerAuthorityList")
    private Authority authorityId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    @JsonIgnoreProperties("authorityPerUserList")
    private User userId;
}
