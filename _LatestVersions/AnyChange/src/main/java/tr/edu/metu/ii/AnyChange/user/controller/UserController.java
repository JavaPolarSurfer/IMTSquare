package tr.edu.metu.ii.AnyChange.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.edu.metu.ii.AnyChange.user.dto.PaymentInformationDTO;
import tr.edu.metu.ii.AnyChange.user.dto.UserDTO;
import tr.edu.metu.ii.AnyChange.user.exceptions.*;
import tr.edu.metu.ii.AnyChange.user.models.AccountType;
import tr.edu.metu.ii.AnyChange.user.models.ConfirmationToken;
import tr.edu.metu.ii.AnyChange.user.services.ConfirmationTokenService;
import tr.edu.metu.ii.AnyChange.user.services.UserService;

import java.util.List;

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

    @GetMapping("/signup")
    String onSignup(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        model.addAttribute("userSignup", true);
        return "signup";
    }

    @PostMapping("/signup")
    String registerUser(@ModelAttribute("user") UserDTO userDto, Model model) {
        model.addAttribute("userSignup", true);
        try {
            userService.createUser(userDto, false);
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

    @GetMapping("/managePersonalInformation")
    String managePersonalInformation(Model model) {
        UserDTO userDto = userService.getCurrentUser();
        model.addAttribute("user", userDto);
        return "managePersonalInformation";
    }

    @PostMapping("/updateCredentials")
    String updateCredentials(@ModelAttribute("user") UserDTO userDto, Model model) {
        model.addAttribute("user", userDto);
        try {
            userService.updateCredentials(userDto);
        } catch (FirstNameEmptyException e) {
            model.addAttribute("errorFirstNameEmpty", "First name cannot be empty!");
            return "managePersonalInformation";
        } catch (LastNameEmptyException e) {
            model.addAttribute("errorLastNameEmpty", "Last name cannot be empty!");
            return "managePersonalInformation";
        } catch (InvalidPhoneNumberException e) {
            model.addAttribute("errorInvalidPhoneNumber", "Phone number is not valid!");
            return "managePersonalInformation";
        }
        return "managePersonalInformation";
    }

    @PostMapping("/updatePassword")
    String updatePassword(@ModelAttribute("user") UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        try {
            userService.updatePassword(userDTO);
        } catch (PasswordEmptyException e) {
            model.addAttribute("errorPasswordEmpty", "Password cannot be empty!");
            return "managePersonalInformation";
        } catch (PasswordTooShortException e) {
            model.addAttribute("errorShortPassword",
                    "Password is too short, it should be at least 8 characters long!");
            return "managePersonalInformation";
        } catch (PasswordNotMatchingException e) {
            model.addAttribute("errorNonMatchingPasswords", "Passwords do not match!");
            return "managePersonalInformation";
        } catch (PasswordSpecialCharactersException e) {
            model.addAttribute("errorPasswordSpecialCharacters",
                    "Password must include at least one special character!");
            return "managePersonalInformation";
        } catch (PasswordCantBeSameException e) {
            model.addAttribute("errorNewPasswordSamePassword",
                    "Password cannot be same as old one!");
            return "managePersonalInformation";
        }
        return "managePersonalInformation";
    }

    @GetMapping("/managePaymentInformation")
    String managePaymentInformation(Model model) {
        PaymentInformationDTO paymentInformationDTO = new PaymentInformationDTO();
        model.addAttribute("paymentInformation", paymentInformationDTO);

        List<PaymentInformationDTO> paymentInformationDTOList = userService.getPaymentOptionsForCurrentUser();
        model.addAttribute("matchingPaymentInformation", paymentInformationDTOList);
        return "managePaymentInformation";
    }

    @PostMapping("/addPaymentInformation")
    String addPaymentInformation(@ModelAttribute("paymentInformation") PaymentInformationDTO paymentInformationDTO,
                                 Model model) {
        try {
            userService.addPaymentInformation(paymentInformationDTO);
        } catch (InvalidCreditCardNumberException e) {
            model.addAttribute("errorInvalidCardNumber",
                    "Card number is invalid!");
            return managePaymentInformation(model);
        } catch (InvalidSecurityNumberException e) {
            model.addAttribute("errorInvalidSecurityCode",
                    "Security code is invalid!");
            return managePaymentInformation(model);
        } catch (InvalidExpirationDateException e) {
            model.addAttribute("errorInvalidExpirationDate",
                    "Expiration date is invalid!");
            return managePaymentInformation(model);
        }
        return managePaymentInformation(model);
    }

    @GetMapping("/deletePaymentInformation")
    String deletePaymentInformation(@RequestParam("id") String id, Model model) {
        userService.deletePaymentInformation(id);
        return managePaymentInformation(model);
    }

    @GetMapping("/manageAccountType")
    String manageAccountType(Model model) {
        UserDTO userDTO = userService.getCurrentUser();
        if (userDTO.getAccountType() == AccountType.STANDART) {
            model.addAttribute("isStandartAccount", true);
            List<PaymentInformationDTO> paymentInformationDTOList = userService.getPaymentOptionsForCurrentUser();
            model.addAttribute("matchingPaymentInformation", paymentInformationDTOList);
        }
        else {
            model.addAttribute("isPremiumAccount", true);
        }
        return "manageAccountType";
    }

    @GetMapping("/upgradeToPremium")
    String upgradeToPremium(@RequestParam("id") String id, Model model) {
        userService.upgradeCurrentUserToPremium(id);
        return manageAccountType(model);
    }

    @GetMapping("/cancelPremium")
    String cancelPremium(Model model) {
        userService.cancelPremiumForCurrentUser();
        return manageAccountType(model);
    }

    @GetMapping("/removeAccount")
    String removeAccount(Model model) {
        userService.removeCurrentAccount();
        return "home";
    }

    @GetMapping("/manageUsers")
    String manageUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "manageUsers";
    }

    @GetMapping("/addUser")
    String addUser(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        model.addAttribute("addUser", true);
        return "signup";
    }

    @PostMapping("/addUser")
    String addUser(@ModelAttribute("user") UserDTO userDto, Model model) {
        model.addAttribute("addUser", true);
        try {
            userService.createUser(userDto, true);
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
        return "redirect:/manageUsers";
    }

    @GetMapping("/removeUser")
    String removeUser(@RequestParam("username") String username) {
        userService.removeUser(username);
        return "redirect:/manageUsers";
    }
}
