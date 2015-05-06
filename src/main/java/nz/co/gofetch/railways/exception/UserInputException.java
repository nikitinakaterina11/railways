package nz.co.gofetch.railways.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * custom exception class for railway application used for handling user criteria errors
 *
 */
public class UserInputException extends WebApplicationException {
	private static final long serialVersionUID = 1L;

	/**
	 * creates exception instance with specified message
	 * 
	 * @param message
	 */
	public UserInputException(String message) {
		super(Response.status(Status.BAD_REQUEST).entity(message).build());
	}

}
