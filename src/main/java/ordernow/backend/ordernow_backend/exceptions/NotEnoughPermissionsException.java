package ordernow.backend.ordernow_backend.exceptions;

public class NotEnoughPermissionsException extends RuntimeException {
    public NotEnoughPermissionsException(String message) {
        super(message);
    }
}
