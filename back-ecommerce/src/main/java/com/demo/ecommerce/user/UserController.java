package com.demo.ecommerce.user;

import com.demo.ecommerce.exceptions.AccountNotConfirmedException;
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
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(userService.register(request));
        } catch (ErrorResponse errorResponse) {
            log.error("email alreafy token  {}", request.getEmail(), errorResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }


    @GetMapping("/confirm")
    public ResponseEntity<?> register(@RequestParam("token") String token) {
        try {
            return ResponseEntity.ok(userService.confirmToken(token));
        } catch (Exception e) {

            log.error("Error trying to confirm email token {} ", token, e);

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> register(
            @RequestBody AuthenticationRequest request
    ) {
        try {
            return ResponseEntity.ok(userService.authenticate(request));
        } catch (BadCredentialsException badCredentialsException) {
            log.error("Bad Credentials {} and {}", request.getEmail(), request.getPassword(), badCredentialsException);
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .success(Boolean.FALSE)
                    .failureReason("Bad credentials")
                    .build();

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(response);
        } catch (AccountNotConfirmedException e) {
            log.error("the Account has not been confirmed yet", e);

            AuthenticationResponse response = AuthenticationResponse.builder()
                    .success(Boolean.FALSE)
                    .failureReason("The account has been not confirmed yet, ")
                    .build();


            if (e.getResendEmail()) {
                response.setFailureReason(response.getFailureReason() + "and email has been re-sent for verify it.");
            } else {
                response.setFailureReason(response.getFailureReason() + " check your email and verify your account.");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(response);
        }
    }
}
