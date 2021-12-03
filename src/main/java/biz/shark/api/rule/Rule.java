package biz.shark.api.rule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface Rule<A extends Annotation> {

	public static <A extends Annotation> Remove<A> remove(Class<A> annotation) {
		return () -> annotation;
	}

	Class<A> annotation();

	boolean followsMissing(Field field, Object value, String name, double quantity);

	boolean followsPresent(A rule, Field field, String name, Object value, double quantity);

	void throwExceptionMissing(Field field, Object value, String name, double quantity);

	void throwExceptionPresent(A rule, Field field, String name, Object value, double quantity);
}
