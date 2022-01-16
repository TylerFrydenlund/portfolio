package biz.shark.app.employee;

import biz.shark.api.Rules;

public class DeleteEmployee {

	@Rules(nullable = false, min = 0)
	public int id;

}
