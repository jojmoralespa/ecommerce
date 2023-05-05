package com.demo.ecommerce.user.tokenRegistration;

import com.demo.ecommerce.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }


    public void setConfirmedAt(String token) throws Exception {

        Optional<ConfirmationToken> confirmationTokenOpt = confirmationTokenRepository.findByToken(token);

        if (confirmationTokenOpt.isEmpty()) {
            throw new Exception("token not found");
        }

        ConfirmationToken tokenToUpdate = confirmationTokenOpt.get();

        tokenToUpdate.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(tokenToUpdate);


    }

    public ConfirmationToken createToken(User user) {

        //Generate a generic token for confirm the account
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(1)) //The token will expired after 1 hour
                .userid(user)
                .build();

        return confirmationToken;

    }
}
