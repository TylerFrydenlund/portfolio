package biz.shark.api.rule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Disables a rule when registered
 * 
 * @author Tyler Frydenlund
 *
 * @param <A> the annotation to remove rules
 */
public interface Remove<A extends Annotation> extends Rule<A> {
	/**
	 * Returns true always.
	 */
	default boolean followsMissing(Field field, Object value, String name, double quantity) {
		return true;
	}

	/**
	 * Returns true always
	 */
	default boolean followsPresent(A rule, Field field, String name, Object value, double quantity) {
		return true;
	}

	/**
	 * Always does nothing
	 */
	default void throwExceptionMissing(Field field, Object value, String name, double quantity) {
		return;
	}

	/**
	 * Always does nothing
	 */
	default void throwExceptionPresent(A rule, Field field, String name, Object value, double quantity) {
		return;
	}

}
