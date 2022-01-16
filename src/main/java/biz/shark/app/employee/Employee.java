package biz.shark.app.employee;

import biz.shark.api.Rules;
import biz.shark.app.Position;

/**
 * A registered employee in the system. This class contains information about an
 * employee
 * 
 * @author Tyler Frydenlund
 *
 */
public class Employee {

	@Rules(min = 0) // There are no negative employee numbers
	public Integer id;

	@Rules(min = 3, max = 20)
	public String firstName;

	@Rules(min = 3, max = 30)
	public String lastName;

	public Position position;

	@Rules(nullable = true) // This is an optional field
	public String favoriteColor;

	public Employee() {

	}

	public Employee(Integer id, String firstName, String lastName, Position position, String favoriteColor) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.favoriteColor = favoriteColor;
	}

	public boolean favoriteColorPresent() {
		return favoriteColor != null;
	}

}
