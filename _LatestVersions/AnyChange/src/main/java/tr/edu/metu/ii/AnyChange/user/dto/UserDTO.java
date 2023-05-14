package tr.edu.metu.ii.AnyChange.user.dto;

import lombok.Getter;
import lombok.Setter;
import tr.edu.metu.ii.AnyChange.user.models.AccountType;

@Getter
@Setter
public class UserDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String matchingPassword;
    private String email;
    private String address;
    private String phoneNumber;
    private AccountType accountType;
}