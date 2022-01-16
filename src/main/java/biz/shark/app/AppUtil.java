package biz.shark.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.BsonDocument;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

public class AppUtil {

	static {
		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
	}

	// This is horrendous practice, but for this Demo, it'll do
	public static MongoClient dbClient() {

		ConnectionString connectionString = new ConnectionString(
				"mongodb+srv://shark_service:TheShark1234@cluster0.mktcd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
		MongoClient mongoClient = MongoClients.create(settings);

		return mongoClient;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> BsonDocument toDoc(T object) {
		// To JSON start
		Moshi moshi = new Moshi.Builder().build();

		JsonAdapter adapter = moshi.adapter(object.getClass());

		String json = adapter.toJson(object);
		// To JSON end

		BsonDocument doc = BsonDocument.parse(json);
		return doc;
	}
}
