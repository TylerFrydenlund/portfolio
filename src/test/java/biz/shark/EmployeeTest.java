package biz.shark;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Test;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import biz.shark.app.Position;
import biz.shark.app.SharkBiz;
import biz.shark.app.employee.DeleteEmployee;
import biz.shark.app.employee.Employee;
import biz.shark.app.employee.NewEmployee;
import biz.shark.app.employee.SearchEmployee;
import biz.shark.app.employee.handlers.DeleteResult;
import biz.shark.app.employee.handlers.PutResult;
import biz.shark.impl.MicroserviceImpl;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class EmployeeTest {

	Moshi moshi = new Moshi.Builder().build();
	int port = new Random().nextInt(65535);
	SharkBiz sharkBiz = new SharkBiz(port);
	MicroserviceImpl microservice;

	{
		try {
			microservice = new MicroserviceImpl(sharkBiz);
			microservice.setLogLevel(Level.OFF);
			microservice.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void put() throws IOException {

		NewEmployee employee = new NewEmployee();

		employee.firstName = "Tyler";
		employee.lastName = "Frydenlund";
		employee.position = Position.DEV;
		employee.favoriteColor = "red";

		JsonAdapter<NewEmployee> adapter = moshi.adapter(NewEmployee.class);

		String json = adapter.toJson(employee);

		HttpRequest request = HttpRequest.put("localhost:" + port + "/employees");

		request.body(json);

		HttpResponse response = request.send();

		JsonAdapter<PutResult> a = moshi.adapter(PutResult.class);

		Assert.assertEquals(response.body(), 200, response.statusCode());

		PutResult result = a.fromJson(response.body());

		Assert.assertEquals(PutResult.ALREADY_EXISTS, result);

	}

	@Test
	public void get() throws IOException {

		SearchEmployee search = new SearchEmployee();

		search.id = 0; // The first employee

		JsonAdapter<SearchEmployee> adapter = moshi.adapter(SearchEmployee.class);

		String json = adapter.toJson(search);

		HttpRequest request = HttpRequest.get("localhost:" + port + "/employees");

		request.body(json);

		HttpResponse response = request.send();

		JsonAdapter<List<Employee>> a = moshi.adapter(Types.newParameterizedType(List.class, Employee.class));

		Assert.assertEquals(response.body(), 200, response.statusCode());

		List<Employee> results = a.fromJson(response.body());

		Assert.assertEquals(1, results.size());
	}

	@Test
	public void delete() throws IOException {

// Puts a new rando employee in
		String uuid = deletePut();
		DeleteEmployee employee = deleteGet(uuid);

		JsonAdapter<DeleteEmployee> adapter = moshi.adapter(DeleteEmployee.class);

		String json = adapter.toJson(employee);

		HttpRequest request = HttpRequest.delete("localhost:" + port + "/employees");

		request.body(json);

		HttpResponse response = request.send();

		JsonAdapter<DeleteResult> a = moshi.adapter(DeleteResult.class);

		Assert.assertEquals(response.body(), 200, response.statusCode());

		DeleteResult result = a.fromJson(response.body());

		Assert.assertEquals(DeleteResult.SUCESS, result);

	}

	String deletePut() {
		NewEmployee employee = new NewEmployee();
		String uuid = UUID.randomUUID().toString().substring(0, 19);

		employee.firstName = uuid;
		employee.lastName = uuid;
		employee.position = Position.JANITOR;
		employee.favoriteColor = uuid;

		JsonAdapter<NewEmployee> adapter = moshi.adapter(NewEmployee.class);

		String json = adapter.toJson(employee);

		HttpRequest request = HttpRequest.put("localhost:" + port + "/employees");

		request.body(json);

		request.send();

		return uuid;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	DeleteEmployee deleteGet(String firstName) throws IOException {
		SearchEmployee search = new SearchEmployee();

		search.firstName = firstName;

		JsonAdapter<SearchEmployee> adapter = moshi.adapter(SearchEmployee.class);

		String json = adapter.toJson(search);

		HttpRequest request = HttpRequest.get("localhost:" + port + "/employees");

		request.body(json);

		HttpResponse response = request.send();

		JsonAdapter<List> a = moshi.adapter(List.class);

		Assert.assertEquals(response.body(), 200, response.statusCode());

		List<Map> results = a.fromJson(response.body());

		DeleteEmployee delete = new DeleteEmployee();
		delete.id = Double.valueOf(results.get(0).get("id").toString()).intValue();
		return delete;
	}
}
