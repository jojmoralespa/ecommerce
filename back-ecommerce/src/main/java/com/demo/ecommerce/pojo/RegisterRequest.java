package com.demo.ecommerce.pojo;

import com.demo.ecommerce.user.AuthorityPerUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<AuthorityPerUser> authorityPerUserList;
}
