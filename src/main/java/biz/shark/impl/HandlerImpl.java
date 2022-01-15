package biz.shark.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.sun.net.httpserver.HttpExchange;

import biz.shark.Util;
import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.api.rule.Rule;

/**
 * 
 * @author Tyler Frydenlund
 *
 * @param <I> The Input class to serialize from JSON Input
 * @param <O> The Output class to deserialize into JSON Output
 */
final class HandlerImpl<I, O> implements Handler<I, O> {

	private final Handler<I, O> original;

	private final String path;
	private final HttpMethod method;;

	private final Class<? extends I> requestAdapter;
	private final Class<? extends O> responseAdapter;
	private final List<Object> customAdapters;

	private final Moshi moshi;
	private final JsonAdapter<? extends I> adapter;

	private final List<Quantifier> quantifiers = new ArrayList<>(ImplUtil.DEFAULT_QUANTIFIERS);
	private final List<Rule<?>> rules = new ArrayList<>(ImplUtil.DEFAULT_RULES);

	private final Map<Class<? extends Annotation>, Rule<?>> mappedRules = new HashMap<>();

	public HandlerImpl(Handler<I, O> handler) {

		Validate.notNull(handler, "Null handlers can not be implemented");
		this.original = handler;

		this.path = original.path();

		this.method = original.method();

		this.requestAdapter = original.requestAdapter();

		this.responseAdapter = original.responseAdapter();

		List<Object> adapters = original.customAdapters();

		this.customAdapters = adapters == null ? List.of() : Collections.unmodifiableList(adapters);

		this.moshi = Util.buildMoshi(customAdapters);

		JsonAdapter<? extends I> adapter = moshi.adapter(requestAdapter()).lenient();

		this.adapter = adapter;
		
		addQuantifiers();
		mapRules();
		
		// Will check for nulls
		validate();
	}

	void mapRules() {
		List<Rule<?>> rules = rules();

		rules = rules == null ? List.of() : Collections.unmodifiableList(rules);

		this.rules.addAll(rules);

		this.rules.forEach(r -> mappedRules.put(r.annotation(), r));
	}

	void addQuantifiers() {
		List<Quantifier> quantifiers = quantifiers();

		quantifiers = quantifiers == null ? List.of() : Collections.unmodifiableList(quantifiers);

		this.quantifiers.addAll(0, quantifiers);
	}

	void validate() {

		Validate.notEmpty(path, "This handler has a null or empty path. This is not allowed");
		Validate.notNull(method, "This handler has a null HttpMethod type. This is not allowed");
		Validate.notNull(requestAdapter, "This handler has a null Request Adapter Class. This is not allowed");
		Validate.noNullElements(customAdapters, "This handler has a null Custom Handler. This is not allowed");
		Validate.noNullElements(quantifiers, "This handler has a null Quantifier. This is not allowed");
		Validate.noNullElements(rules, "This handler has a null Rule. This is not allowed");

	}

	@Override
	public String path() {
		return path;
	}

	@Override
	public HttpMethod method() {
		return method;
	}

	@Override
	public Class<? extends I> requestAdapter() {
		return requestAdapter;
	}

	@Override
	public Class<? extends O> responseAdapter() {
		return responseAdapter;
	}

	@Override
	public List<Object> customAdapters() {
		return customAdapters;
	}

	@Override
	public List<Quantifier> quantifiers() {
		return Collections.unmodifiableList(quantifiers); // No Modifications should be made
	}

	@Override
	public List<Rule<?>> rules() {
		return Collections.unmodifiableList(rules); // No modifications should be made
	}

	@Override
	public O handle(HttpExchange exchange, I requestBody) throws IOException {
		return original.handle(exchange, requestBody);
	}

	public void handle(HttpExchange exchange) throws IOException {
		InputStream in = exchange.getRequestBody();

		String json = new String(in.readAllBytes());

		// Prevents a json malformed exception from occuring if nothing is submited
		if (json.isBlank()) {
			json = "\"\"";// double open and close qoutes
		}

		try {
			handle(exchange, json);
		} catch (JsonDataException e) {
			// Is thrown because a field does not follow is specified rules
			jsonDataException(exchange, e);
		} catch (Throwable e) {
			// If this is happens, the client just needs to know it was on our end
			internalServerException(exchange, e);
		}

	}

	@SuppressWarnings("unchecked")
	void handle(HttpExchange exchange, String json)
			throws IOException, IllegalArgumentException, IllegalAccessException {
		I request = adapter.fromJson(json);

		// Will throw a JSON Data Exception if the JSON values do not meet the specified
		// Rules
		ImplUtil.checkRules(mappedRules, quantifiers, request);

		// Calles the implemented Handlers request method
		O response = handle(exchange, request);

		Class<? extends O> responseAdapter = responseAdapter();

		// To JSON Start
		@SuppressWarnings({ "rawtypes" })
		JsonAdapter adapter = moshi.adapter(responseAdapter);

		json = adapter.toJson(response);

		// To JSON End

		Util.writeResponse(exchange, 200, json);
	}

	void internalServerException(HttpExchange exchange, Throwable e) throws IOException {

		e.printStackTrace();

		String report = Util.errorReport(new Exception("Internal server exception " + e.getMessage()));

		Util.writeResponse(exchange, 500, report);
	}

	void jsonDataException(HttpExchange exchange, JsonDataException e) throws IOException {

		String report = Util.errorReport(e);
		Util.writeResponse(exchange, 400, report);
	}
}
