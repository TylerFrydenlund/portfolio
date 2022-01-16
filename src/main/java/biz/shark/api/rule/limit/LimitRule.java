package biz.shark.api.rule.limit;

import java.lang.reflect.Field;

import org.apache.commons.lang3.Range;

import com.squareup.moshi.JsonDataException;

import biz.shark.Util;
import biz.shark.api.rule.Remove;
import biz.shark.api.rule.Rule;

/**
 * The Limit rule restricts the bounds of a fields qunatative value
 * 
 * @see Limit
 * @author Tyler Frydenlund
 * 
 **/
public final class LimitRule implements Rule<Limit> {

	public static LimitRule create() {
		return new LimitRule();
	}

	public static Remove<Limit> remove() {
		return Rule.remove(Limit.class);
	}

	private LimitRule() {

	}

	@Override
	public Class<Limit> annotation() {
		return Limit.class;
	}

	@Override
	public boolean followsMissing(Field field, Object value, String name, double quantity) {
		return true;
	}

	@Override
	public boolean followsPresent(Limit rule, Field field, String name, Object value, double quantity) {

		Range<Double> range = Util.toRange(rule);

		return range.contains(quantity);
	}

	@Override
	public void throwExceptionMissing(Field field, Object value, String name, double quantity) {
		return;
	}

	@Override
	public void throwExceptionPresent(Limit rule, Field field, String name, Object value, double quantity) {
		Range<Double> range = Util.toRange(rule);

		String message = "Provided field named, {0} was out of bounds. This value must be between of these length/values{min:{1},max:{2}}";

		message = message.formatted(name, range.getMinimum(), range.getMaximum());

		throw new JsonDataException(message);
	}

}
