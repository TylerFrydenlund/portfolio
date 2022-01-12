package biz.shark;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Test;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import biz.shark.app.Position;
import biz.shark.app.SharkBiz;
import biz.shark.app.employee.NewEmployee;
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
	public void put() {

		NewEmployee employee = new NewEmployee("Tyler", "Frydenlund", Position.DEV, "red");

		JsonAdapter<NewEmployee> adapter = moshi.adapter(NewEmployee.class);

		String json = adapter.toJson(employee);

		HttpRequest request = HttpRequest.put("localhost:" + port + "/employees");

		request.body(json);

		HttpResponse response = request.send();

		Assert.assertEquals(response.body(), 200, response.statusCode());

	}
}
