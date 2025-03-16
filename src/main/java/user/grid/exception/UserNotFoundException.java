package user.grid.exception;

public class UserNotFoundException extends RuntimeException {
   
	private static final long serialVersionUID = 194007539408486950L;

	public UserNotFoundException(String message) {
        super(message);
    }
}
