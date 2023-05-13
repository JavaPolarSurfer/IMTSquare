package tr.edu.metu.ii.AnyChange.user.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tr.edu.metu.ii.AnyChange.product.dto.ProductDTO;
import tr.edu.metu.ii.AnyChange.user.dto.UserDTO;
import tr.edu.metu.ii.AnyChange.user.exceptions.*;
import tr.edu.metu.ii.AnyChange.user.models.ConfirmationToken;
import tr.edu.metu.ii.AnyChange.user.models.User;
import tr.edu.metu.ii.AnyChange.user.models.UserRole;
import tr.edu.metu.ii.AnyChange.user.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void validatePassword(String password, String matchingPassword) throws PasswordEmptyException, PasswordNotMatchingException, PasswordTooShortException, PasswordSpecialCharactersException {
        if (password.isEmpty()) {
            throw new PasswordEmptyException("Password cannot be empty!");
        }
        if (!password.equals(matchingPassword)) {
            throw new PasswordNotMatchingException("Passwords do not match!");
        }
        if (password.length() < 8) {
            throw new PasswordTooShortException("Password is too short, it should be at least 8 characters long!");
        }
        Pattern regex = Pattern.compile("[^A-Za-z0-9]");
        Matcher matcher = regex.matcher(password);
        if (!matcher.find()) {
            throw new PasswordSpecialCharactersException("Password must include at least one special character!");
        }
    }

    public void sendConfirmationToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        sendConfirmationEmail(user.getEmail(), confirmationToken.getToken());
    }

    public boolean checkIfUserExists(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        return optionalUser.isPresent();
    }

    public void createUser(UserDTO userDTO) throws UsernameAlreadyExistsException, PasswordTooShortException, PasswordSpecialCharactersException, PasswordEmptyException, PasswordNotMatchingException, FirstNameEmptyException, LastNameEmptyException {
        if (checkIfUserExists(userDTO.getEmail())) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }
        if (userDTO.getFirstName().isEmpty()) {
            throw new FirstNameEmptyException("First name cannot be empty!");
        }
        if (userDTO.getLastName().isEmpty()) {
            throw new LastNameEmptyException("Last name cannot be empty!");
        }
        validatePassword(userDTO.getPassword(), userDTO.getMatchingPassword());
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setRole(UserRole.USER);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        createUser(user);
        sendConfirmationToken(user);
    }

    public void confirmUser(String token) throws ExpiredTokenException, InvalidConfirmationTokenException {
        ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationToken(token);
        if (LocalDateTime.now().minus(120, ChronoUnit.SECONDS).isAfter(confirmationToken.getCreatedDate())) {
            throw new ExpiredTokenException("This confirmation token is expired!");
        }

        User user = confirmationToken.getUser();
        user.setEnabled(true);
        confirmationTokenService.deleteConfirmationToken(confirmationToken);
    }

    public void confirmPasswordResetToken(ConfirmationToken confirmationToken) throws ExpiredTokenException {
        if (LocalDateTime.now().minus(120, ChronoUnit.SECONDS).isAfter(confirmationToken.getCreatedDate())) {
            throw new ExpiredTokenException("This confirmation token is expired!");
        }
        confirmationTokenService.deleteConfirmationToken(confirmationToken);
    }

    public void createUser(User user) throws UsernameAlreadyExistsException {
        userRepository.save(user);
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

    public void sendResetPasswordEmail(String email, String token) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Reset Password Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Click below link to reset your password." + "http://localhost:8080/resetPassword?token="
                        + token);

        confirmationEmailSenderService.sendEmail(mailMessage);
    }

    public void sendResetPasswordToken(User user) {
        ConfirmationToken token = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(token);
        sendResetPasswordEmail(user.getEmail(), token.getToken());
    }

    public void forgotPassword(String username) throws UsernameNotFoundException {
        User user = (User)loadUserByUsername(username);
        sendResetPasswordToken(user);
    }

    public void resetPassword(String token, String password, String matchingPassword) throws PasswordTooShortException, PasswordSpecialCharactersException, PasswordEmptyException, PasswordNotMatchingException, InvalidConfirmationTokenException, ExpiredTokenException {
        ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationToken(token);
        if (LocalDateTime.now().minus(120, ChronoUnit.SECONDS).isAfter(confirmationToken.getCreatedDate())) {
            confirmationTokenService.deleteConfirmationToken(confirmationToken);
            throw new ExpiredTokenException("This confirmation token is expired!");
        }
        validatePassword(password, matchingPassword);

        User user = confirmationToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        confirmationTokenService.deleteConfirmationToken(confirmationToken);
    }

    public void updateCredentials(UserDTO userDto) throws FirstNameEmptyException, LastNameEmptyException, InvalidPhoneNumberException {
        if (userDto.getFirstName().isEmpty()) {
            throw new FirstNameEmptyException("First name cannot be empty!");
        }
        if (userDto.getLastName().isEmpty()) {
            throw new LastNameEmptyException("Last name cannot be empty!");
        }
        validatePhoneNumber(userDto.getPhoneNumber());

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Email not found!");
        }

        User user = optionalUser.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
    }

    private void validatePhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        if (phoneNumber.isEmpty()) {
            return;
        }
        Pattern regex = Pattern.compile("^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
        Matcher matcher = regex.matcher(phoneNumber);
        if (!matcher.find()) {
            throw new InvalidPhoneNumberException("Phone number is not valid!");
        }
    }

    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            User user = (User)loadUserByUsername(name);

            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setPassword(user.getPassword());
            userDTO.setAddress(user.getAddress());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            return userDTO;
        }
        throw new RuntimeException("Could not access authentication!");
    }

    public void updatePassword(UserDTO userDTO) throws PasswordTooShortException, PasswordSpecialCharactersException, PasswordEmptyException, PasswordNotMatchingException {
        validatePassword(userDTO.getPassword(), userDTO.getMatchingPassword());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            User user = (User) loadUserByUsername(name);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
    }
}
