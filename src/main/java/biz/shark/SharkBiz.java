package biz.shark;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import biz.shark.api.Handler;
import biz.shark.api.Microservice;

public class SharkBiz implements Microservice {

	@Override
	public String name() {
		return "shark-biz";
	}

	@Override
	public Logger logger() {
		return Logger.getLogger("Shark Biz");
	}

	@Override
	public Map<String, Object> configDefaults() {
		return null;
	}

	@Override
	public List<Handler<?, ?>> handlers() {
		return null;
	}

}
