package biz.shark;

import org.junit.Test;

import biz.shark.impl.ImplUtil;
import biz.shark.non_tests.TestRequest;

public class RulesTest {

	@Test
	public void passLimit() throws IllegalArgumentException, IllegalAccessException {

		TestRequest request = new TestRequest();

		ImplUtil.checkRules(ImplUtil.DEFAULT_QUANTIFIERS, request);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void failLimit() throws IllegalArgumentException, IllegalAccessException {
		TestRequest request = new TestRequest();
		request.id = Integer.MAX_VALUE;// 0-10 is our range

		ImplUtil.checkRules(ImplUtil.DEFAULT_QUANTIFIERS, request);
	}

	@Test
	public void passNullable() throws IllegalArgumentException, IllegalAccessException {

		TestRequest request = new TestRequest();

		ImplUtil.checkRules(ImplUtil.DEFAULT_QUANTIFIERS, request);
	}

	@Test(expected = NullPointerException.class)
	public void failNullable() throws IllegalArgumentException, IllegalAccessException {

		TestRequest request = new TestRequest();
		request.color = null;

		ImplUtil.checkRules(ImplUtil.DEFAULT_QUANTIFIERS, request);
	}
}
