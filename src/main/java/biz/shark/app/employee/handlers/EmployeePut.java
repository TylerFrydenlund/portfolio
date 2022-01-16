package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.api.rule.Rule;
import biz.shark.app.AppUtil;
import biz.shark.app.employee.NewEmployee;
import biz.shark.app.employee.handlers.results.PutResult;

public class EmployeePut implements Handler<NewEmployee, PutResult> {

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
	public Class<? extends PutResult> responseAdapter() {
		return PutResult.class;
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
	public PutResult handle(HttpExchange exchange, NewEmployee employee) throws IOException {

		if (inDatabase(employee)) {
			return PutResult.ALREADY_EXISTS;
		}

		if (addToDatabase(employee)) {
			return PutResult.SUCESS;
		}

		return PutResult.FAILED;
	}

	Document toDoc(NewEmployee employee) {
		// To JSON start
		Moshi moshi = new Moshi.Builder().build();

		JsonAdapter<NewEmployee> adapter = moshi.adapter(NewEmployee.class);

		String json = adapter.toJson(employee);
		// To JSON end

		Document doc = Document.parse(json);
		return doc;
	}

	boolean inDatabase(NewEmployee employee) {

		MongoClient client = AppUtil.dbClient();

		MongoDatabase database = client.getDatabase("sharkbiz");

		MongoCollection<Document> collection = database.getCollection("employees");

		Document doc = toDoc(employee);

		return collection.find(doc).iterator().hasNext();

	}

	boolean addToDatabase(NewEmployee employee) {

		MongoClient client = AppUtil.dbClient();

		MongoDatabase database = client.getDatabase("sharkbiz");

		MongoCollection<Document> collection = database.getCollection("employees");

		Document doc = toDoc(employee);

		collection.insertOne(doc);
		return true;

	}

}
