package com.demo.ecommerce.user;

import com.demo.ecommerce.config.JWTService;
import com.demo.ecommerce.exceptions.AccountNotConfirmedException;
import com.demo.ecommerce.exceptions.ErrorResponse;
import com.demo.ecommerce.pojo.AuthenticationRequest;
import com.demo.ecommerce.pojo.AuthenticationResponse;
import com.demo.ecommerce.pojo.RegisterRequest;
import com.demo.ecommerce.user.tokenRegistration.ConfirmationToken;
import com.demo.ecommerce.user.tokenRegistration.ConfirmationTokenService;
import com.demo.ecommerce.utils.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    private final AuthorityRepository authorityRepository;

    private final ConfirmationTokenService confirmationTokenService;

    private final EmailService emailService;

    private final TemplateEngine templateEngine;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${mail.url.front.confirm}")
    private String urlConfirm;

    public AuthenticationResponse register(RegisterRequest request) throws ErrorResponse {

        //TODO verificar que el email y password cumplan con el formato
        //TODO usando un REGEX o Validator

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userPerAuthorityList(request.getAuthorityPerUserList())
                .enabled(false)
                .locked(false)
                .build();


        List<AuthorityPerUser> authorityPerUserList = request.getAuthorityPerUserList();
        for (AuthorityPerUser authorityPerUser : authorityPerUserList) {
            authorityPerUser.setUserId(user);
            Authority authority = authorityPerUser.getAuthorityId();
            authorityPerUser.setAuthorityId(authorityRepository.findById(authority.getId()).get());
        }


        //Verify the email has not been saved before
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            throw new ErrorResponse("Email already taken.");
        }


        //Save user into DB
        userRepository.save(user);

        //Generate conformation token
        ConfirmationToken confirmationToken = confirmationTokenService.createToken(user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO Send the Email Verification
        sendConfirmationToken(user, confirmationToken.getToken());


        return AuthenticationResponse.builder()
                .token(confirmationToken.getToken())
                .success(Boolean.TRUE)
                .build();

    }

    private void sendConfirmationToken(User user, String token) {
        String[] emailUser = {user.getEmail()};

        Context context = new Context();
        Map<String, Object> model = new HashMap<>();
        model.put("userName", user.getFirstName() + " " + user.getLastName());
        model.put("linkConfirm", urlConfirm + token);
        context.setVariables(model);
        String htmlText = templateEngine.process("confirmAccount", context);

        emailService.sendEmail(emailUser, "Confirm your Account", htmlText, true);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AccountNotConfirmedException, BadCredentialsException, UsernameNotFoundException {

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

        //TODO validate if the user has it account enable if not resend email
        if (!user.getEnabled()) {

            //check if the token hasn't expired yet
            List<ConfirmationToken> confirmationTokenList = user.getConfirmationTokenList();

            log.info("Tokens {}", confirmationTokenList);

            if (confirmationTokenList.isEmpty() || confirmationTokenList.get(0).getExpiresAt().isBefore(LocalDateTime.now())) {

                ConfirmationToken confirmationToken = confirmationTokenService.createToken(user);
                confirmationTokenService.saveConfirmationToken(confirmationToken);

                sendConfirmationToken(user, confirmationToken.getToken());

                throw new AccountNotConfirmedException(true);

            }

            throw new AccountNotConfirmedException(false);
        }


        //Generate token
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .success(Boolean.TRUE)
                .build();

    }

    @Transactional //because we are changing data no just saving
    public String confirmToken(String token) throws Exception {


        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow();

        if (confirmationToken.getConfirmedAt() != null) {
            throw new Exception("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new Exception("token has expired");
        }


        confirmationTokenService.setConfirmedAt(token);
        enableUserAccount(confirmationToken.getUserid().getEmail());

        return "Confirmed";
    }

    private void enableUserAccount(String email) throws UsernameNotFoundException {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty())
            throw new UsernameNotFoundException("User not found");

        User userUpdate = userOpt.get();
        userUpdate.setEnabled(Boolean.TRUE);

        userRepository.save(userUpdate);

    }


}
