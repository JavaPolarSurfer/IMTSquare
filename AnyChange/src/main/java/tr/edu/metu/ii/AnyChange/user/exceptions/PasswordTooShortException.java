package tr.edu.metu.ii.AnyChange.user.exceptions;

public class PasswordTooShortException extends Exception {
    public PasswordTooShortException(String message) {
        super(message);
    }
}
