package com.demo.ecommerce.user;

import com.demo.ecommerce.config.JWTService;
import com.demo.ecommerce.model.OrderProduct;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.pojo.AuthenticationRequest;
import com.demo.ecommerce.pojo.AuthenticationResponse;
import com.demo.ecommerce.pojo.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    private final AuthorityRepository authorityRepository;

    public AuthenticationResponse register(RegisterRequest request) {

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authorityPerUserList(request.getAuthorityPerUserList())
                .build();


        List<AuthorityPerUser> authorityPerUserList = request.getAuthorityPerUserList();
        for (AuthorityPerUser authorityPerUser : authorityPerUserList) {
            authorityPerUser.setUserId(user);
            Authority authority = authorityPerUser.getAuthorityId();
            authorityPerUser.setAuthorityId(authorityRepository.findById(authority.getId()).get());
        }



//        User user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .authorityPerUserList(request.getAuthorityPerUserList())
//                .role(Role.ROLE_USER)
//                .build();

        //Save user into DB
        userRepository.save(user);

        //Generate token from user
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {


        //Let springBoot make the authentication process
        //through the authenticationManager and using UsernamePasswordAuthenticationToken
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        //if success validation continue and look for the user
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found for the provided email."));

        //Generate token
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();


    }
}
