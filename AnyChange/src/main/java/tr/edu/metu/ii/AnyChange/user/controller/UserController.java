package tr.edu.metu.ii.AnyChange.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.edu.metu.ii.AnyChange.user.dto.UserDTO;
import tr.edu.metu.ii.AnyChange.user.exceptions.*;
import tr.edu.metu.ii.AnyChange.user.models.ConfirmationToken;
import tr.edu.metu.ii.AnyChange.user.services.ConfirmationTokenService;
import tr.edu.metu.ii.AnyChange.user.services.UserService;

@Controller
@AllArgsConstructor
public class UserController {
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

    @GetMapping("/products")
    String onProducts() {
        return "products";
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
            userService.createUser(userDto);
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("errorUserAlreadyExists", "User already exists!");
            return "signup";
        } catch (PasswordEmptyException e) {
            model.addAttribute("errorPasswordEmpty", "Password cannot be empty!");
            return "signup";
        } catch (PasswordTooShortException e) {
            model.addAttribute("errorShortPassword",
                    "Password is too short, it should be at least 8 characters long!");
            return "signup";
        } catch (PasswordNotMatchingException e) {
            model.addAttribute("errorNonMatchingPasswords", "Passwords do not match!");
            return "signup";
        } catch (PasswordSpecialCharactersException e) {
            model.addAttribute("errorPasswordSpecialCharacters",
                    "Password must include at least one special character!");
            return "signup";
        } catch (FirstNameEmptyException e) {
            model.addAttribute("errorFirstNameEmpty", "First name cannot be empty!");
            return "signup";
        } catch (LastNameEmptyException e) {
            model.addAttribute("errorLastNameEmpty", "Last name cannot be empty!");
            return "signup";
        }
        return "home";
    }

    @GetMapping("/confirm")
    String confirmUser(@RequestParam("token") String token, Model model) {
        try {
            userService.confirmUser(token);
        } catch (InvalidConfirmationTokenException e) {
            return "home";
        } catch (ExpiredTokenException e) {
            return "expiredToken";
        }
        return "login";
    }

    @GetMapping("/resendConfirmationToken")
    String resendConfirmationToken(@RequestParam("token") String token) {
        try {
            ConfirmationToken oldConfirmationToken = confirmationTokenService.findConfirmationToken(token);
            userService.sendConfirmationToken(oldConfirmationToken.getUser());
            confirmationTokenService.deleteConfirmationToken(oldConfirmationToken);
        } catch (InvalidConfirmationTokenException e) {
            return "home";
        }
        return "login";
    }

    @GetMapping("/forgotPassword")
    String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    String forgotPassword(String username, Model model) {
        try {
            userService.forgotPassword(username);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("errorUsernameNotFound", "User does not exist!");
            return "forgotPassword";
        }
        return "login";
    }

    @GetMapping("/resetPassword")
    String resetPassword(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    String resetPassword(@RequestParam("token") String token, String password, String matchingPassword, Model model) {
        try {
            userService.resetPassword(token, password, matchingPassword);
        } catch (InvalidConfirmationTokenException e) {
            return "home";
        } catch (ExpiredTokenException e) {
            return "expiredResetPasswordToken";
        } catch (PasswordEmptyException e) {
            model.addAttribute("errorPasswordEmpty", "Password cannot be empty!");
            return "resetPassword";
        } catch (PasswordTooShortException e) {
            model.addAttribute("errorShortPassword",
                    "Password is too short, it should be at least 8 characters long!");
            return "resetPassword";
        } catch (PasswordNotMatchingException e) {
            model.addAttribute("errorNonMatchingPasswords", "Passwords do not match!");
            return "resetPassword";
        } catch (PasswordSpecialCharactersException e) {
            model.addAttribute("errorPasswordSpecialCharacters",
                    "Password must include at least one special character!");
            return "resetPassword";
        }
        return "login";
    }
}
