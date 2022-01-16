package biz.shark.app;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class AppUtil {
	
	// This is horrendous practice, but for this Demo, it'll do
	public static MongoClient dbClient() {
		ConnectionString connectionString = new ConnectionString(
				"mongodb+srv://shark_service:TheShark1234@cluster0.mktcd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
		MongoClient mongoClient = MongoClients.create(settings);

		return mongoClient;
	}
}
