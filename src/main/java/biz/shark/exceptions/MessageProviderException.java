package biz.shark.exceptions;

/**
 * This exception is thrown when the system fails to provided a message
 * describing what went wrong. It will help tell the user it is a server side
 * issue.
 * 
 * @author Tyler Frydenlund
 *
 */
public class MessageProviderException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String INFO = " (Internal server exception)";

	public MessageProviderException() {
		super();
	}

	public MessageProviderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message + INFO, cause, enableSuppression, writableStackTrace);
	}

	public MessageProviderException(String message, Throwable cause) {
		super(message + INFO, cause);
	}

	public MessageProviderException(String message) {
		super(message + INFO);
	}

	public MessageProviderException(Throwable cause) {
		super(cause);
	}

}
