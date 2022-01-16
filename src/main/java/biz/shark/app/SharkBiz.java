package biz.shark.app;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import biz.shark.api.Handler;
import biz.shark.api.Microservice;
import biz.shark.app.employee.handlers.EmployeeGet;
import biz.shark.app.employee.handlers.EmployeePut;

/**
 * The important system information of this demo app.
 * 
 * @author Tyler Frydenlund
 *
 */
public class SharkBiz implements Microservice {

	private final int port;

	public SharkBiz(int port) {
		this.port = port;
	}

	@Override
	public String name() {
		return "Shark Biz";
	}

	@Override
	public Logger logger() {
		return Logger.getLogger(name());
	}

	@Override
	public Map<String, Object> configDefaults() {
		return Map.of("port", port);
	}

	@Override
	public List<Handler<?, ?>> handlers() {
		return List.of(new EmployeePut(), new EmployeeGet());
	}

}
