package name.edds.mileageservice.user;

public class InvalidUserException extends Exception {

    public InvalidUserException(String errorMessage) {
        super(errorMessage);
    }

}
