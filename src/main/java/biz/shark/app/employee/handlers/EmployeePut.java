package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.ClientSession;
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
import biz.shark.app.MongoUtil;
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

		MongoClient client = MongoUtil.client();
		ClientSession session = client.startSession();

		try {

			MongoDatabase database = client.getDatabase("sharkbiz");

			MongoCollection<Document> collection = database.getCollection("employees");

			Document doc = toDoc(employee);

			return collection.find(doc).iterator().hasNext();
		} finally {
			session.close();
		}

	}

	boolean addToDatabase(NewEmployee employee) {

		MongoClient client = MongoUtil.client();
		ClientSession session = client.startSession();
		try {

			MongoDatabase database = client.getDatabase("sharkbiz");

			MongoCollection<Document> collection = database.getCollection("employees");

			Document doc = toDoc(employee);

			return collection.insertOne(doc).getInsertedId() != null;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return false;
	}

}
