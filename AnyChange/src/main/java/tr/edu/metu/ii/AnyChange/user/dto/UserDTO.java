package tr.edu.metu.ii.AnyChange.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String matchingPassword;
    private String email;
}