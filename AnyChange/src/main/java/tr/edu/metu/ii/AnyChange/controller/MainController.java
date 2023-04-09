package tr.edu.metu.ii.AnyChange.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.edu.metu.ii.AnyChange.dto.UserDTO;
import tr.edu.metu.ii.AnyChange.user.*;

@Controller
@AllArgsConstructor
public class MainController {
    final private UserService userService;
    final private ConfirmationTokenService confirmationTokenService;
    @GetMapping("/")
    String onMainPage() {
        return "home";
    }

    @GetMapping("/login")
    String onLogin() {
        return "login";
    }

    @GetMapping("/hello")
    String onHello() {
        return "hello";
    }

    @GetMapping("/signup")
    String onSignup(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "signup";
    }

    @PostMapping("/signup")
    String registerUser(@ModelAttribute("user") UserDTO userDto, Model model) {
        try {
            boolean hasError = false;
            if (userDto.getFirstName().isEmpty()) {
                model.addAttribute("errorFirstNameEmpty", "First name cannot be empty!");
                hasError = true;
            }
            if (userDto.getLastName().isEmpty()) {
                model.addAttribute("errorLastNameEmpty", "Last name cannot be empty!");
                hasError = true;
            }
            if (userDto.getPassword().isEmpty()) {
                model.addAttribute("errorPasswordEmpty", "Password cannot be empty!");
                hasError = true;
            }
            if (!userDto.getPassword().equals(userDto.getMatchingPassword())) {
                model.addAttribute("errorNonMatchingPasswords", "Passwords do not match!");
                hasError = true;
            }

            if (hasError) {
                return "signup";
            }
            else {
                userService.createUser(userDto);
            }
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("errorUserAlreadyExists", "User already exists!");
            return "signup";
        }
        return "home";
    }

    @GetMapping("/confirm")
    String confirmUser(@RequestParam("token") String token) {
        try {
            ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationToken(token);
            userService.confirmUser(confirmationToken);
            return "login";
        } catch (InvalidConfirmationTokenException e) {
            return "home";
        }
    }
}
