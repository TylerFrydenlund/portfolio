package biz.shark.api.rule.value;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.squareup.moshi.JsonDataException;

import biz.shark.api.rule.Remove;
import biz.shark.api.rule.Rule;

/**
 * The value rule is a rule that acts identical to an Enumerator (enum) where
 * fields can be marked with an The "Values" annotation. Marked fields will only
 * accept the provided quantative values
 * 
 * @see Values
 * @author Tyler Frydenlund
 *
 */
public class ValueRule implements Rule<Values> {

	public static ValueRule create() {
		return new ValueRule();
	}

	public static Remove<Values> remove() {
		return Rule.remove(Values.class);
	}

	private ValueRule() {

	}

	@Override
	public Class<Values> annotation() {
		return Values.class;
	}

	@Override
	public boolean followsMissing(Field field, Object value, String name, double quantity) {
		return true;
	}

	@Override
	public boolean followsPresent(Values rule, Field field, String name, Object value, double quantity) {

		for (double q : rule.values()) {

			if (q == quantity) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void throwExceptionMissing(Field field, Object value, String name, double quantity) {
		return;
	}

	@Override
	public void throwExceptionPresent(Values rule, Field field, String name, Object value, double quantity) {
		throw new JsonDataException("Provided field named, " + name + " was not one of the allowed values. values"
				+ Arrays.toString(rule.values()));

	}

}
