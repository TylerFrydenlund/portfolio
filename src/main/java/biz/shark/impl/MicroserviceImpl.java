package biz.shark.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;
import org.yaml.snakeyaml.Yaml;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import biz.shark.Util;
import biz.shark.api.Handler;
import biz.shark.api.Microservice;

/**
 * The bread an butter of the backend system. Handles registering HTTP Handlers, system
 * configuration.
 * 
 * @author Tyler Frydenlund
 *
 */
public final class MicroserviceImpl implements Microservice {

	private final Map<String, HandlerGroup> groups;

	private final Microservice original;

	private final String name;
	private final Logger logger;
	
	// The defaults in for the config
	private final Map<String, Object> defaults;
	private final List<Handler<?, ?>> handlers;

	private final File config;
	private final Yaml yaml;

	private final Executor executor;
	private HttpServer server;

	public MicroserviceImpl(Microservice microservice) throws IOException {

		Validate.notNull(microservice, "Null microservices can not be implemented");

		this.original = microservice;
		this.name = original.name();
		this.logger = original.logger();

		Validate.notEmpty(name, "Microservices must have name. Yours was blank/null");
		Validate.notNull(logger, "The provided microservice logger was null");

		this.defaults = Util.loadDefaults(microservice);

		this.handlers = Util.loadHandlers(microservice);

		this.groups = ImplUtil.groupHandlers(handlers);

		logger.info("Loading microservice configuration...");

		this.config = new File(name + "-config.yaml");

		this.yaml = Util.createYaml();

		Util.dumpDefaults(yaml, config, defaults);

		logger.info("Loading configuration complete.");

		this.executor = Executors.newCachedThreadPool();

	}

	public void setLogLevel(Level level) {
		logger.setLevel(level);
	}

	public Map<String, HandlerGroup> getGroups() {
		return groups;
	}

	public void start() throws IOException {
		logger.info("Intializing Http Server on port " + port());

		try {
			this.server = HttpServerProvider.provider().createHttpServer(new InetSocketAddress(port()), 0);

			server.setExecutor(executor);

			for (Entry<String, HandlerGroup> entry : groups.entrySet()) {
				server.createContext(entry.getKey(), entry.getValue());
			}

			server.start();

		} catch (IllegalStateException e) {

			logger.severe("The microservice may not be started more than once! Already running...");
			e.printStackTrace();
		}

		logger.info(name + " microservice has been intialzied and started!");
	}

	public void stop() {
		server.stop(1);
	}

	public void stop(int delay) {
		server.stop(delay);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Logger logger() {
		return logger;
	}

	@Override
	public Map<String, Object> configDefaults() {
		return defaults;
	}

	@Override
	public List<Handler<?, ?>> handlers() {
		return handlers;
	}

	public HttpServer getServer() {
		return server;
	}

	public int port() throws FileNotFoundException {
		return (int) defaults.get("port");

	}

}
