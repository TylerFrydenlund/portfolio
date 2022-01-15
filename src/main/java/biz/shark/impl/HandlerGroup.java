package biz.shark.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import biz.shark.Util;
import biz.shark.api.HttpMethod;

/**
 * An HTTP Handler that holds all methods to a provided path. This class is
 * designed to help reduce developer overhead when making new Handlers for a
 * Microservice. It is only implemented by the system.
 * 
 * @author Tyler Frydenlund
 *
 */

final class HandlerGroup implements HttpHandler {

	final Map<HttpMethod, HandlerImpl<?, ?>> methods = new HashMap<>();

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod().toUpperCase());

		HandlerImpl<?, ?> handler = methods.get(method);

		// No method by the provided type so we must tell the client we dont support it
		if (handler == null) {

			String report = Util.errorReport(new UnsupportedOperationException("Unsupported method"));

			Util.writeResponse(exchange, 405, report);

		} else {

			handler.handle(exchange);
		}

		exchange.getResponseBody().close();
	}

}
