package com.demo.ecommerce.user;

import com.demo.ecommerce.model.OrderProduct;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
    public Authority(Role name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private  Role name;

    @OneToMany(mappedBy = "authorityId", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("authorityId")
    private List<AuthorityPerUser> authorityPerUserList;

}
