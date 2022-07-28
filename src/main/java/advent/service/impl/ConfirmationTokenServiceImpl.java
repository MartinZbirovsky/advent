package advent.service.impl;

import advent.model.ConfirmationToken;
import advent.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    /**
     * Save new token to database.
     * @param token - token
     */
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    /**
     * Find token by his value.
     * @param token - Token value
     * @return token
     */
    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
