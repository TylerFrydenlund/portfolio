package biz.shark.non_tests;

import biz.shark.api.Rules;

public class TestRequest {

	@Rules(min = 0, max = 10)
	public int id = 5;

	public String color = "red";

}
