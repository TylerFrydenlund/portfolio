package biz.shark.non_tests;

import biz.shark.api.rule.limit.Limit;
import biz.shark.api.rule.value.Values;

public class TestRequest {

	@Limit(min = 0, max = 10)
	public int id = 5;

	public String color = "red";

	@Values(values = { 1, 2, 3 })
	public int speed = 1;

}
