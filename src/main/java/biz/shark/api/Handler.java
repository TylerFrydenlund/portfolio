package biz.shark.api;

import java.io.IOException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.rule.Rule;

public interface Handler<I, O> {

	String path();

	HttpMethod method();

	Class<? extends I> requestAdapter();

	Class<? extends O> responseAdapter();

	List<Object> customAdapters();

	List<Quantifier> quantifiers();

	List<Rule<?>> rules();

	O handle(HttpExchange exchange, I requestBody) throws IOException;

}
