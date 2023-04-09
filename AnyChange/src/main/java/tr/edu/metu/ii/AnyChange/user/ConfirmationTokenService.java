package tr.edu.metu.ii.AnyChange.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public void deleteConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.delete(confirmationToken);
    }

    public ConfirmationToken findConfirmationToken(String token) throws InvalidConfirmationTokenException {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findConfirmationTokenByToken(token);
        if (confirmationToken.isEmpty()) {
            throw new InvalidConfirmationTokenException("Token is invalid!");
        }
        return confirmationToken.get();
    }
}
