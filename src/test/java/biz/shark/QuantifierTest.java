package biz.shark;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import biz.shark.api.Quantifier;
import biz.shark.impl.quantifiers.ArrayQuantifier;
import biz.shark.impl.quantifiers.CollectionQuantifier;
import biz.shark.impl.quantifiers.MapQuantifier;
import biz.shark.impl.quantifiers.NumberQuantifier;
import biz.shark.impl.quantifiers.StringQuantifier;

public class QuantifierTest {

	@Test
	public void array() {

		int[] subject = { 1, 2, 3 };
		Quantifier quantifier = new ArrayQuantifier();

		Assert.assertTrue(quantifier.isType(subject));
		Assert.assertEquals(subject.length * 1.0d, quantifier.quanity(subject), 0.0d);

	}

	@Test
	public void collection() {

		List<Integer> subject = List.of(1, 2, 3);
		Quantifier quantifier = new CollectionQuantifier();

		Assert.assertTrue(quantifier.isType(subject));
		Assert.assertEquals(subject.size() * 1.0d, quantifier.quanity(subject), 0.0d);

	}

	@Test
	public void map() {

		Map<Integer, Integer> subject = Map.of(1, 1, 2, 2, 3, 3);
		Quantifier quantifier = new MapQuantifier();

		Assert.assertTrue(quantifier.isType(subject));
		Assert.assertEquals(subject.size() * 1.0d, quantifier.quanity(subject), 0.0d);

	}

	@Test
	public void number() {

		int subject = 0;
		Quantifier quantifier = new NumberQuantifier();

		Assert.assertTrue(quantifier.isType(subject));
		Assert.assertEquals(subject * 1.0d, quantifier.quanity(subject), 0.0d);

	}

	@Test
	public void string() {

		String subject = "hello";
		Quantifier quantifier = new StringQuantifier();

		Assert.assertTrue(quantifier.isType(subject));
		Assert.assertEquals(subject.length() * 1.0d, quantifier.quanity(subject), 0.0d);

	}
}
