package com.demo.ecommerce.user;

import com.demo.ecommerce.exceptions.ErrorResponse;
import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.pojo.AuthenticationRequest;
import com.demo.ecommerce.pojo.AuthenticationResponse;
import com.demo.ecommerce.pojo.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    private final AuthorityRepository authorityRepository;

    //Instancia de Logger
    private final Logger log = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/list")
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }



    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> register(
            @RequestBody AuthenticationRequest request
    ) {
        try {
            return ResponseEntity.ok(userService.authenticate(request));
        } catch (BadCredentialsException badCredentialsException) {
            log.error("Bad Credentials {} and {}", request.getEmail(), request.getPassword(), badCredentialsException);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Bad credentials"));
        } catch (UsernameNotFoundException usernameNotFoundException) {
            log.error("Non existing user with email {}", request.getEmail(), usernameNotFoundException);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Non existing user."));
        }catch ( DisabledException disabledException){

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("The account has been not confirmed yet."));
        }

    }


}
