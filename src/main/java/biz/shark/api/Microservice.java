package biz.shark.api;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Microservices are minaturized systems/apps. Classes may implement this
 * Interface and use the methods to return important information about the
 * microservice.
 * 
 * @author Tyler Frydenlund
 *
 */
public interface Microservice {
	/**
	 * @return The name of the microservice
	 */
	String name();

	/**
	 * @return A logger used for logging information about the service's current
	 *         state
	 */
	Logger logger();

	/**
	 * @return A map of default configuration options for this service
	 */
	Map<String, Object> configDefaults();

	/**
	 * @return A list of all the handlers this service contains
	 */
	List<Handler<?, ?>> handlers();

}
