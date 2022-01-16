package biz.shark.app.employee;

import com.squareup.moshi.Json;

import biz.shark.api.rule.limit.Limit;
import biz.shark.api.rule.nullable.Nullable;
import biz.shark.app.Position;

/**
 * A registered employee in the system. This class contains information about an
 * employee
 * 
 * @author Tyler Frydenlund
 *
 */
public class Employee {

	@Json(name = "employeeId")
	@Limit(min = 0) // There are no negative employee numbers
	public int number;

	@Limit(min = 3, max = 20)
	public String firstName;

	@Limit(min = 3, max = 30)
	public String lastName;

	public Position position;

	@Nullable // This is an optional field
	public String favoriteColor;

	public Employee(int number, String firstName, String lastName, Position position, String favoriteColor) {
		super();
		this.number = number;
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.favoriteColor = favoriteColor;
	}

	public boolean favoriteColorPresent() {
		return favoriteColor != null;
	}

}
