package biz.shark.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.commons.lang3.Validate;

import biz.shark.api.rule.Rule;
import biz.shark.exceptions.MessageProviderException;
import biz.shark.exceptions.RuleFollowException;

final class RuleImpl<A extends Annotation> implements Rule<A> {

	private final Rule<A> original;
	private final Class<A> annotation;

	public RuleImpl(Rule<A> rule) {
		Validate.notNull(rule, "Null rules can not be implemented");

		this.original = rule;
		this.annotation = getAnnotation();

		Validate.notNull(annotation, "A rule can not have a null annotation");
	}

	Class<A> getAnnotation() {

		Class<A> annotation = annotation();

		Validate.notNull(annotation, "A rules annotation may not be null");

		return annotation;

	}

	public Rule<A> getOriginal() {
		return original;
	}

	@Override
	public Class<A> annotation() {
		return annotation;
	}

	@Override
	public boolean followsMissing(Field field, Object value, String name, double quantity) {
		try {
			return original.followsMissing(field, value, name, quantity);
		} catch (Throwable t) {

			t.printStackTrace();

			throw new RuleFollowException(false, name);
		}
	}

	@Override
	public boolean followsPresent(A rule, Field field, String name, Object value, double quantity) {
		try {
			return original.followsPresent(rule, field, name, value, quantity);
		} catch (Throwable t) {

			t.printStackTrace();

			throw new RuleFollowException(true, name);
		}
	}

	@Override
	public void throwExceptionMissing(Field field, Object value, String name, double quantity) {
		try {

			original.throwExceptionMissing(field, value, name, quantity);

		} catch (Throwable t) {
			t.printStackTrace();

			throw new MessageProviderException(
					"Failed to provided proper exception message while rule was missing for: {field=" + name + "}");
		}
	}

	@Override
	public void throwExceptionPresent(A rule, Field field, String name, Object value, double quantity) {
		try {
			original.throwExceptionPresent(rule, field, name, value, quantity);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new MessageProviderException(
					"Failed to provided proper exception message while rule was present for: {field=" + name + "}");
		}
	}

}
