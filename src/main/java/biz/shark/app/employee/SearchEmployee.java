package biz.shark.app.employee;

import biz.shark.api.Rules;
import biz.shark.app.Position;

public class SearchEmployee {

	@Rules(min = 1, max = 50)
	public int limit = 10;

	@Rules(nullable = true, min = 0)
	public Integer id;

	@Rules(nullable = true, min = 3, max = 20)
	public String firstName;

	@Rules(nullable = true, min = 3, max = 30)
	public String lastName;

	@Rules(nullable = true)
	public Position position;

	@Rules(nullable = true)
	public String favoriteColor;

	public boolean favoriteColorPresent() {
		return favoriteColor != null;
	}

}
