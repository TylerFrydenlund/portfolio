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

		this.path = handler.getPath();

		this.method = handler.getMethod();

		this.responseAdapter = handler.getResponseAdapter(null);
		this.requestAdapter = handler.getRequestAdapter();

		this.customAdapters = handler.getCustomAdapters();

		this.moshi = Util.buildMoshi(customAdapters);

		JsonAdapter<? extends I> adapter = moshi.adapter(requestAdapter()).lenient();

		this.adapter = adapter;

		this.quantifiers.addAll(0, handler.getQuantifiers());
		this.rules.addAll(handler.getRules());

		this.rules.forEach(r -> mappedRules.put(r.annotation(), r));

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
		return Collections.unmodifiableList(quantifiers);
	}

	@Override
	public List<Rule<?>> rules() {
		return Collections.unmodifiableList(rules);
	}

	@Override
	public O handle(HttpExchange exchange, I requestBody) throws IOException {
		return original.handle(exchange, requestBody);
	}

	@SuppressWarnings("unchecked")
	public void handle(HttpExchange exchange) throws IOException {
		InputStream in = exchange.getRequestBody();

		String json = new String(in.readAllBytes());

		try {
			I request = adapter.fromJson(json);

			ImplUtil.checkRules(mappedRules, quantifiers, request);

			O response = handle(exchange, request);

			Class<? extends O> responseAdapter = getResponseAdapter((Class<? extends O>) response.getClass());

			@SuppressWarnings({ "rawtypes" })
			JsonAdapter adapter = moshi.adapter(responseAdapter);

			json = adapter.toJson(response);

			Util.writeResponse(exchange, 200, json);

		} catch (JsonDataException e) {
			String report = Util.errorReport(e);
			Util.writeResponse(exchange, 400, report);

		} catch (Throwable e) {
			e.printStackTrace();

			String report = Util.errorReport(new Exception("Internal server exception"));

			Util.writeResponse(exchange, 500, report);
		}

	}

}
