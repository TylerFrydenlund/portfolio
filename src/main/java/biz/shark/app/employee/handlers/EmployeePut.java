package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.api.rule.Rule;
import biz.shark.app.employee.NewEmployee;

public class EmployeePut implements Handler<NewEmployee, biz.shark.app.employee.handlers.EmployeePut.Result> {

	enum Result {
		SUCESS, ALREADY_EXISTS, FAILED;
	}

	@Override
	public String path() {
		return "/employees";
	}

	@Override
	public HttpMethod method() {
		return HttpMethod.PUT;
	}

	@Override
	public Class<? extends NewEmployee> requestAdapter() {
		return NewEmployee.class;
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
	public Result handle(HttpExchange exchange, NewEmployee employee) throws IOException {

		if (inDatabase(employee)) {
			return Result.ALREADY_EXISTS;
		}

		if (addToDatabase(employee)) {
			return Result.SUCESS;
		}

		return Result.FAILED;
	}

	boolean inDatabase(NewEmployee employee) {
		return true;
	}

	boolean addToDatabase(NewEmployee employee) {
		return false;
	}

}
