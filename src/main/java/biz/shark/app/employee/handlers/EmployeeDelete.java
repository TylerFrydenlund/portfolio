package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.api.rule.Rule;
import biz.shark.app.employee.handlers.results.DeleteResult;

public class EmployeeDelete implements Handler<Integer, DeleteResult> {

	@Override
	public String path() {
		return "/employees";
	}

	@Override
	public HttpMethod method() {
		return HttpMethod.DELETE;
	}

	@Override
	public Class<? extends Integer> requestAdapter() {
		return Integer.class;
	}

	@Override
	public Class<? extends DeleteResult> responseAdapter() {
		return DeleteResult.class;
	}

	@Override
	public List<Object> customAdapters() {
		return List.of();
	}

	@Override
	public List<Quantifier> quantifiers() {
		return List.of();
	}

	@Override
	public List<Rule<?>> rules() {
		return List.of();
	}

	@Override
	public DeleteResult handle(HttpExchange exchange, Integer employee) throws IOException {

		if (!find(employee)) {
			return DeleteResult.NOT_FOUND;
		}

		if (remove(employee)) {
			return DeleteResult.SUCESS;
		}

		return DeleteResult.FAIL;
	}

	boolean find(Integer employee) {
		return false;
	}

	boolean remove(Integer employee) {
		return false;
	}

}
