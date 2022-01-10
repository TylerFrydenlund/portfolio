package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.api.rule.Rule;
import biz.shark.app.employee.Employee;

public class Put implements Handler<Employee, biz.shark.app.employee.handlers.Put.Result> {

	enum Result {
		SUCESS, ALREADY_EXISTS;
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
	public Class<? extends Employee> requestAdapter() {
		return Employee.class;
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
	public Result handle(HttpExchange exchange, Employee employee) throws IOException {
		
		
		
		return null;
	}

}
