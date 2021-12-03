package biz.shark.exceptions;

public class RuleFollowException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RuleFollowException() {
		super();
	}

	public RuleFollowException(boolean present, String fieldName) {
		super("Failed to check if value " + fieldName + " follows rule while " + (present ? "present" : "missing")
				+ " (Internal server exception)");
	}

	public RuleFollowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RuleFollowException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuleFollowException(String message) {
		super(message);
	}

	public RuleFollowException(Throwable cause) {
		super(cause);
	}

}
