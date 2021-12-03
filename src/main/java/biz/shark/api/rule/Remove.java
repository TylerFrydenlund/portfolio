package biz.shark.api.rule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface Remove<A extends Annotation> extends Rule<A> {

	default boolean followsMissing(Field field, Object value, String name, double quantity) {
		return true;
	}

	default boolean followsPresent(A rule, Field field, String name, Object value, double quantity) {
		return true;
	}

	default void throwExceptionMissing(Field field, Object value, String name, double quantity) {
		return;
	}

	default void throwExceptionPresent(A rule, Field field, String name, Object value, double quantity) {
		return;
	}

}
