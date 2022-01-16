package biz.shark.api;

/**
 * Used to help quantify variable types in order to help limit/restrict what the
 * system will accept.
 * 
 * @see Rule
 * 
 * @author Tyler Frydenlund
 *
 */
public interface Quantifier {
	/**
	 * @param object to be checked
	 * @return if the provided object is the type
	 */
	boolean isType(Object object);

	/**
	 * @param object to be quantified
	 * @return The quantity of the value
	 */
	double quanity(Object object);

}
