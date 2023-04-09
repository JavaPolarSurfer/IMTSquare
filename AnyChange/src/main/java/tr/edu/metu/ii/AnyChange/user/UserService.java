package tr.edu.metu.ii.AnyChange.user;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tr.edu.metu.ii.AnyChange.dto.UserDTO;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    final private UserRepository userRepository;
    final private BCryptPasswordEncoder passwordEncoder;
    final private ConfirmationTokenService confirmationTokenService;
    final private ConfirmationEmailSenderService confirmationEmailSenderService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Email not found!");
        }
        return optionalUser.get();
    }

    public boolean checkIfUserExists(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        return optionalUser.isPresent();
    }

    public void createUser(UserDTO userDTO) throws UsernameAlreadyExistsException {
        if (checkIfUserExists(userDTO.getEmail())) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setRole(UserRole.USER);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        sendConfirmationEmail(user.getEmail(), confirmationToken.getToken());
    }

    public void confirmUser(ConfirmationToken confirmationToken) {
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        confirmationTokenService.deleteConfirmationToken(confirmationToken);
    }

    public void sendConfirmationEmail(String email, String token) {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Thank you for registering. Please click on the below link to activate your account." + "http://localhost:8080/confirm?token="
                        + token);

        confirmationEmailSenderService.sendEmail(mailMessage);
    }
}
