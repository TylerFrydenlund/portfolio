package biz.shark.app.employee;

import biz.shark.api.rule.limit.Limit;
import biz.shark.api.rule.nullable.Nullable;
import biz.shark.app.Position;

public class NewEmployee {

	@Limit(min = 3, max = 20)
	public String firstName;

	@Limit(min = 3, max = 30)
	public String lastName;

	public Position position;

	@Nullable // This is an optional field
	public String favoriteColor;

	public NewEmployee(String firstName, String lastName, Position position, String favoriteColor) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.favoriteColor = favoriteColor;
	}

	public boolean favoriteColorPresent() {
		return favoriteColor != null;
	}

}
