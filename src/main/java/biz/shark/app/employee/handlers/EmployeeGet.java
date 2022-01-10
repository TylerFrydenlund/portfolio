package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.api.rule.Rule;
import biz.shark.app.employee.Employee;

@SuppressWarnings("rawtypes")
public class EmployeeGet implements Handler<Employee, List> {

	@Override
	public String path() {
		return "/employees";
	}

	@Override
	public HttpMethod method() {
		return HttpMethod.GET;
	}

	@Override
	public Class<? extends Employee> requestAdapter() {
		return Employee.class;
	}

	@Override
	public Class<? extends List> responseAdapter() {
		return List.class;
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
	public List<Employee> handle(HttpExchange exchange, Employee requestBody) throws IOException {
		List<Employee> employees = new ArrayList<>();

		return employees;
	}
	
	

}
