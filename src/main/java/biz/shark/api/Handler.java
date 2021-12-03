package biz.shark.api;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.rule.Rule;

public interface Handler<I, O> {

	String path();

	default String getPath() {

		String path = path();

		Validate.notEmpty(path, "This handler has a null or empty path. This is not allowed");

		return path;
	}

	HttpMethod method();

	default HttpMethod getMethod() {

		HttpMethod method = method();

		Validate.notNull(method, "This handler has a null HttpMethod type. This is not allowed");

		return method;
	}
	
	Class<? extends I> requestAdapter();

	default Class<? extends I> getRequestAdapter() {
		Class<? extends I> clazz = requestAdapter();

		Validate.notNull(clazz, "This handler has a null Request Adapter Class. This is not allowed");

		return clazz;

	}

	Class<? extends O> responseAdapter();

	default Class<? extends O> getResponseAdapter(Class<? extends O> adapter) {

		return responseAdapter() == null ? adapter : responseAdapter();
	}

	List<Object> customAdapters();

	default List<Object> getCustomAdapters() {
		List<Object> adapters = customAdapters();

		adapters = adapters == null ? List.of() : Collections.unmodifiableList(adapters);

		Validate.noNullElements(adapters, "This handler has a null Custom Handler. This is not allowed");

		return adapters;

	}

	List<Quantifier> quantifiers();

	default List<Quantifier> getQuantifiers() {

		List<Quantifier> quantifiers = quantifiers();

		quantifiers = quantifiers == null ? List.of() : Collections.unmodifiableList(quantifiers);

		Validate.noNullElements(quantifiers, "This handler has a null Quantifier. This is not allowed");

		return quantifiers;

	}

	List<Rule<?>> rules();

	default List<Rule<?>> getRules() {

		List<Rule<?>> rules = rules();

		rules = rules == null ? List.of() : Collections.unmodifiableList(rules);

		Validate.noNullElements(rules, "This handler has a null Rule. This is not allowed");

		return rules;

	}

	O handle(HttpExchange exchange, I requestBody) throws IOException;

}
