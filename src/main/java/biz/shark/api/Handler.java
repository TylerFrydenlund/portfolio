package biz.shark.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

/**
 * A microservice handler used for processing requests
 * 
 * @author Tyler Frydenlund
 *
 * @param <I> Input Type
 * @param <O> Outpuut Type
 */
public interface Handler<I, O> {
	/**
	 * The path as used in a url of some sort trailing after host connection info
	 * such as sharkbix.com
	 * 
	 * Ex: /about
	 * 
	 * @return A string representation of the path
	 */
	String path();

	/**
	 * 
	 * @return The HTTP Method this handler will handle
	 */
	HttpMethod method();

	/**
	 * 
	 * @return The class used to serialize incoming json messages
	 */
	Type requestAdapter();

	/**
	 * 
	 * @return The class used to deserialize outgoing json messages
	 */
	Type responseAdapter();

	/**
	 * 
	 * @return A list of Moshi Adapters used for custom serialization
	 */
	List<Object> customAdapters();

	/**
	 * 
	 * @return Additional Quantifiers to be used on fields
	 */
	List<Quantifier> quantifiers();


	/**
	 * Called when incoming messages are received. The returned value will be sent to the client in JSON format
	 * 
	 * @param exchange
	 * @param requestBody
	 * @return The outgoing JSON Message
	 * @throws IOException
	 */
	O handle(HttpExchange exchange, I requestBody) throws IOException;

}
