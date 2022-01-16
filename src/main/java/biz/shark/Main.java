package biz.shark;

import java.io.IOException;
import java.util.logging.Logger;

import biz.shark.app.SharkBiz;
import biz.shark.impl.MicroserviceImpl;

public class Main implements Runnable {

	private static final Logger LOGGER = Logger.getLogger("Shark Biz Bootstrap");

	private static final Main INSTANCE = new Main(); // Used for the shutdown hook

	private static final SharkBiz sharkBiz = new SharkBiz(8080); // Our ONLY instance of the service
	private static MicroserviceImpl microservice; // The implementation of the service

	// No where else should or needs this
	private Main() {
	}

	/**
	 * The main method of the application
	 * 
	 * @param args is unused but required by the JVM
	 * @throws IOException if the Implementation of the Microservice fails to load
	 */

	public static void main(String[] args) throws IOException {
		logStart();

		LOGGER.info("Adding shut downhook");
		// Create a shut down hook to safely handle when the
		// system is stopped
		Runtime.getRuntime().addShutdownHook(new Thread(INSTANCE));
		LOGGER.info("Sucess!");

		LOGGER.info("Implementing Shark Biz...");
		microservice = new MicroserviceImpl(sharkBiz);
		LOGGER.info("Complete!");

		LOGGER.info("Starting Shark Biz...");
		microservice.start();
	}

	static void logStart() {

		LOGGER.info("--------------------------\n");

		LOGGER.info("Buissness is like sharks.");
		LOGGER.info("The big shark eats the little shark,");
		LOGGER.info("then the little shark eats the");
		LOGGER.info("littler shark all the way down");
		LOGGER.info("until the single cell shark.\n");

		LOGGER.info("--------------------------\n");

		LOGGER.info("Loading Shark Biz...");
	}

	/**
	 * Called by the shut down hook when the application stops
	 */
	@Override
	public void run() {
		LOGGER.info("Stopping Shark Biz");
		microservice.stop(1);
		LOGGER.info("Stopped...");
	}

	public static Logger logger() {
		return LOGGER;
	}

}
