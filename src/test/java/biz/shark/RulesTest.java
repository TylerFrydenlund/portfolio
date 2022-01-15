package biz.shark;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.squareup.moshi.JsonDataException;

import biz.shark.api.rule.limit.Limit;
import biz.shark.api.rule.limit.LimitRule;
import biz.shark.api.rule.nullable.Nullable;
import biz.shark.api.rule.nullable.NullableRule;
import biz.shark.api.rule.value.ValueRule;
import biz.shark.api.rule.value.Values;
import biz.shark.impl.ImplUtil;
import biz.shark.impl.quantifiers.NumberQuantifier;
import biz.shark.impl.quantifiers.StringQuantifier;
import biz.shark.non_tests.TestRequest;

public class RulesTest {

	@Test
	public void passLimit() throws IllegalArgumentException, IllegalAccessException {

		TestRequest request = new TestRequest();

		ImplUtil.checkRules(Map.of(Limit.class, LimitRule.create()), List.of(new NumberQuantifier()), request);
	}

	@Test(expected = JsonDataException.class)
	public void failLimit() throws IllegalArgumentException, IllegalAccessException {
		TestRequest request = new TestRequest();
		request.id = Integer.MAX_VALUE;// 0-10 is our range

		ImplUtil.checkRules(Map.of(Limit.class, LimitRule.create()), List.of(new NumberQuantifier()), request);
	}

	@Test
	public void passNullable() throws IllegalArgumentException, IllegalAccessException {

		TestRequest request = new TestRequest();

		ImplUtil.checkRules(Map.of(Nullable.class, NullableRule.create()), List.of(new StringQuantifier()), request);
	}

	@Test
	public void passValues() throws IllegalArgumentException, IllegalAccessException {

		TestRequest request = new TestRequest();

		ImplUtil.checkRules(Map.of(Values.class, ValueRule.create()), List.of(new NumberQuantifier()), request);
	}

	@Test(expected = JsonDataException.class)
	public void failValues() throws IllegalArgumentException, IllegalAccessException {
		TestRequest request = new TestRequest();
		request.speed = 10; // not one of our values

		ImplUtil.checkRules(Map.of(Values.class, ValueRule.create()), List.of(new NumberQuantifier()), request);
	}

}
