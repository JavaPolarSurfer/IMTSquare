package tr.edu.metu.ii.AnyChange.user.exceptions;

public class PasswordNotMatchingException extends Exception {
    public PasswordNotMatchingException(String message) {
        super(message);
    }
}
