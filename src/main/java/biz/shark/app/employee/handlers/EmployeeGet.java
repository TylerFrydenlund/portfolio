package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.app.AppUtil;
import biz.shark.app.employee.Employee;
import biz.shark.app.employee.SearchEmployee;

public class EmployeeGet implements Handler<SearchEmployee, List<Employee>> {

	Moshi moshi = new Moshi.Builder().build();

	@Override
	public String path() {
		return "/employees";
	}

	@Override
	public HttpMethod method() {
		return HttpMethod.GET;
	}

	@Override
	public Type requestAdapter() {
		return SearchEmployee.class;
	}

	@Override
	public Type responseAdapter() {
		return Types.newParameterizedType(List.class, Employee.class);
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
	public List<Employee> handle(HttpExchange exchange, SearchEmployee search) throws IOException {
		List<Employee> employees = new ArrayList<>();

		MongoClient client = AppUtil.dbClient();

		try {

			MongoDatabase database = client.getDatabase("sharkbiz");

			MongoCollection<Document> collection = database.getCollection("employees");

			Employee employee = new Employee(search.id, search.firstName, search.lastName, search.position,
					search.favoriteColor);

			BsonDocument doc = AppUtil.toDoc(employee);

			BsonDocument filter = new BsonDocument();

			if (search.id != null) {
				filter.append("id", new BsonInt32(-1));
			}
			if (search.firstName != null) {
				filter.append("firstName", new BsonInt32(-1));
			}
			if (search.lastName != null) {
				filter.append("lastName", new BsonInt32(-1));
			}

			MongoCursor<Document> cursor = collection.find(doc).sort(filter).limit(search.limit).iterator();

			while (cursor.hasNext()) {
				employees.add(docToEmployee(cursor.next()));
			}

			return employees;

		} finally {
			client.close();
		}
	}

	Employee docToEmployee(Document document) throws IOException {
		JsonAdapter<Employee> adapter = moshi.adapter(Employee.class);

		return adapter.fromJson(document.toJson());
	}

}
