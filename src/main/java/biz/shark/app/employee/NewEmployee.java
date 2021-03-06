package biz.shark.app.employee;

import biz.shark.api.Rules;
import biz.shark.app.Position;

public class NewEmployee {

	@Rules(min = 3, max = 20)
	public String firstName;

	@Rules(min = 3, max = 30)
	public String lastName;

	public Position position;

	@Rules(nullable = true) // This is an optional field
	public String favoriteColor;

	public boolean favoriteColorPresent() {
		return favoriteColor != null;
	}

}
