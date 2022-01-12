package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.api.rule.Rule;
import biz.shark.app.employee.handlers.EmployeeDelete.Result;

public class EmployeeDelete implements Handler<Integer, Result> {

	enum Result {
		NOT_FOUND, SUCESS, FAIL;
	}

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
	public Class<? extends Result> responseAdapter() {
		return Result.class;
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
	public Result handle(HttpExchange exchange, Integer employee) throws IOException {

		if (!find(employee)) {
			return Result.NOT_FOUND;
		}

		if (remove(employee)) {
			return Result.SUCESS;
		}

		return Result.FAIL;
	}

	boolean find(Integer employee) {
		return false;
	}

	boolean remove(Integer employee) {
		return false;
	}

}
