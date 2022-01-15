package biz.shark.app;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoUtil {

	// This is horrendous practice, but for this Demo, it'll do

	public static MongoClient client() {
		ConnectionString connectionString = new ConnectionString(
				"mongodb+srv://shark-service:<TheShark1234>@cluster0.mktcd.mongodb.net/sharkbiz?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();

		MongoClient client = MongoClients.create(settings);

		return client;
	}

}
