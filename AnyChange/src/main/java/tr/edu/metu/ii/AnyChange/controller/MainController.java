package tr.edu.metu.ii.AnyChange.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tr.edu.metu.ii.AnyChange.dto.UserDTO;
import tr.edu.metu.ii.AnyChange.user.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
            if (userDto.getPassword().length() < 8) {
                model.addAttribute("errorShortPassword",
                        "Password is too short, it should be at least 8 characters long!");
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
    String confirmUser(@RequestParam("token") String token, Model model) {
        try {
            ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationToken(token);
            if (LocalDate.now().minus(120, ChronoUnit.SECONDS).isBefore(confirmationToken.getCreatedDate())) {
                return "expiredToken";
            }
            userService.confirmUser(confirmationToken);
            return "login";
        } catch (InvalidConfirmationTokenException e) {
            return "home";
        }
    }

    @GetMapping("/resendConfirmationToken")
    String resendConfirmationToken(@RequestParam("token") String token) {
        try {
            ConfirmationToken oldConfirmationToken = confirmationTokenService.findConfirmationToken(token);
            userService.sendConfirmationToken(oldConfirmationToken.getUser());
            confirmationTokenService.deleteConfirmationToken(oldConfirmationToken);
            return "login";
        } catch (InvalidConfirmationTokenException e) {
            return "home";
        }
    }
}
