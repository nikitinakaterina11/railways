package nz.co.gofetch.railways.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * custom exception class for railway application used for railway
 * configuration exceptions 
 *
 */
public class RailwayException extends WebApplicationException {
	private static final long serialVersionUID = 1L;

	/**
	 * creates exception instance with specified message
	 * 
	 * @param message user friendly error message
	 */
	public RailwayException(String message) {
		super(Response.status(Status.BAD_REQUEST).entity(message).build());
	}

	/**
	 * creates exception instance with specified message and status code
	 * 
	 * @param message user friendly error message
	 * @param status HTTP status code
	 */
	public RailwayException(String message, int status) {
		super(Response.status(status).entity(message).build());
	}

}
