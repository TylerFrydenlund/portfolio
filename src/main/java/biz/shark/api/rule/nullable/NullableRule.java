package biz.shark.api.rule.nullable;

import java.lang.reflect.Field;

import com.squareup.moshi.JsonDataException;

import biz.shark.api.rule.Remove;
import biz.shark.api.rule.Rule;

public final class NullableRule implements Rule<Nullable> {

	public static NullableRule create() {
		return new NullableRule();
	}

	public static Remove<Nullable> remove() {
		return Rule.remove(Nullable.class);
	}

	private NullableRule() {

	}

	@Override
	public Class<Nullable> annotation() {
		return Nullable.class;
	}

	@Override
	public boolean followsMissing(Field field, Object value, String name, double quantity) {
		return quantity != Double.NaN;
	}

	@Override
	public boolean followsPresent(Nullable rule, Field field, String name, Object value, double quantity) {
		return rule.nullable() || (!rule.nullable() && quantity != Double.NaN);
	}

	@Override
	public void throwExceptionMissing(Field field, Object value, String name, double quantity) {
		throw new JsonDataException("Provided field named, " + name + " was null, this value must be set");
	}

	@Override
	public void throwExceptionPresent(Nullable rule, Field field, String name, Object value, double quantity) {
		throw new JsonDataException("Provided field named, " + name + " was null, this value must be set");
	}

}
