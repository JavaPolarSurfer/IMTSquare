package tr.edu.metu.ii.AnyChange.user;

public class InvalidConfirmationTokenException extends Exception {
    public InvalidConfirmationTokenException(String message) {
        super(message);
    }
}
