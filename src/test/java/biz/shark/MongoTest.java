package biz.shark;

import org.junit.Assert;
import org.junit.Test;

import biz.shark.app.MongoUtil;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class MongoTest {

	@Test
	public void connection() {

		HttpRequest request = HttpRequest.post(
				"https://data.mongodb-api.com/app/data-wrkmo/endpoint/data/beta/action/findOne");

		request.header("api-key", MongoUtil.API_KEY);
		request.header("Access-Control-Request-Headers", "*");
		request.header("Content-Type", "application/json");

		request.mediaType("application/json");

		request.body(
				"{\n    \"collection\":\"employees\",\n    \"database\":\"sharkbiz\",\n    \"dataSource\":\"Cluster0\",\n    \"projection\": {\"_id\": 1}\n\n}");

		HttpResponse response = request.send();

		Assert.assertEquals("Excpected status code 200 for ok", 200, response.statusCode());

	}

}
