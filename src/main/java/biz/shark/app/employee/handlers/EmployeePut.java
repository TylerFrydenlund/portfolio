package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.app.AppUtil;
import biz.shark.app.employee.Employee;
import biz.shark.app.employee.NewEmployee;

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
	public Type requestAdapter() {
		return NewEmployee.class;
	}

	@Override
	public Type responseAdapter() {
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
	public PutResult handle(HttpExchange exchange, NewEmployee employee) throws IOException {

		if (inDatabase(employee)) {
			return PutResult.ALREADY_EXISTS;
		}

		if (addToDatabase(employee)) {
			return PutResult.SUCESS;
		}

		return PutResult.FAILED;
	}

	boolean inDatabase(NewEmployee employee) {

		MongoClient client = AppUtil.dbClient();

		try {
			MongoDatabase database = client.getDatabase("sharkbiz");

			MongoCollection<Document> collection = database.getCollection("employees");

			BsonDocument doc = AppUtil.toDoc(employee);

			return collection.find(doc).iterator().hasNext();
		} finally {
			client.close();
		}
	}

	boolean addToDatabase(NewEmployee employee) {

		MongoClient client = AppUtil.dbClient();

		try {
			MongoDatabase database = client.getDatabase("sharkbiz");

			MongoCollection<Document> collection = database.getCollection("employees");

			int id = nextId();

			Employee e = new Employee(id, employee.firstName, employee.lastName, employee.position,
					employee.favoriteColor);

			BsonDocument doc = AppUtil.toDoc(e);

			collection.insertOne(Document.parse(doc.toJson()));
			return true;

		} finally {
			client.close();
		}
	}

	int nextId() {
		MongoClient client = AppUtil.dbClient();

		try {
			MongoDatabase database = client.getDatabase("sharkbiz");
			MongoCollection<Document> collection = database.getCollection("employees");

			BsonDocument filter = new BsonDocument();

			filter.append("id", new BsonInt32(-1));

			MongoCursor<Document> cursor = collection.find().sort(filter).limit(1).iterator();

			return cursor.hasNext() ? BsonDocument.parse(cursor.next().toJson()).getInt32("id").intValue() + 1 : 0;

		} finally {
			client.close();
		}
	}

}
