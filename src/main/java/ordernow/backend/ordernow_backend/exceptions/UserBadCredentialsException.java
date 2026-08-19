package ordernow.backend.ordernow_backend.exceptions;

public class UserBadCredentialsException extends RuntimeException {
    public UserBadCredentialsException(String message) {
        super(message);
    }
}
