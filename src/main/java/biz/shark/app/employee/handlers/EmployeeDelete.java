package biz.shark.app.employee.handlers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.HttpMethod;
import biz.shark.api.Quantifier;
import biz.shark.app.AppUtil;
import biz.shark.app.employee.DeleteEmployee;

public class EmployeeDelete implements Handler<DeleteEmployee, DeleteResult> {

	@Override
	public String path() {
		return "/employees";
	}

	@Override
	public HttpMethod method() {
		return HttpMethod.DELETE;
	}

	@Override
	public Type requestAdapter() {
		return DeleteEmployee.class;
	}

	@Override
	public Type responseAdapter() {
		return DeleteResult.class;
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
	public DeleteResult handle(HttpExchange exchange, DeleteEmployee employee) throws IOException {

		MongoClient client = AppUtil.dbClient();
		try {
			MongoDatabase database = client.getDatabase("sharkbiz");

			MongoCollection<Document> collection = database.getCollection("employees");

			BsonDocument filter = new BsonDocument();

			filter.append("id", new BsonInt32(employee.id));

			return collection.deleteOne(filter).wasAcknowledged() ? DeleteResult.SUCESS : DeleteResult.NOT_FOUND;
		} finally {
			client.close();
		}
	}

}
