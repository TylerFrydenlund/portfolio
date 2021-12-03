package biz.shark.api;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public interface Microservice {

	String name();

	Logger logger();

	Map<String, Object> configDefaults();

	List<Handler<?, ?>> handlers();

}
