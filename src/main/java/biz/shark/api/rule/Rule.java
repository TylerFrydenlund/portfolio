package biz.shark.api.rule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Rules are way to check the quantity of a variable and restrict its value.
 * This helps moderate what values can be used.
 * 
 * @author Tyler Frydenlund
 *
 * @param <A> An annotation to apply this rule to
 */
public interface Rule<A extends Annotation> {

	/**
	 * Removes a rule for an annotation. This rule must be registered in its place
	 * to remove a default rule.
	 * 
	 * @see LimitRule
	 * @see NullableRule
	 * @see ValueRule
	 * 
	 * @param <A>        the annotation to target
	 * @param annotation the class of the annotation to target
	 * @return The remove rule for the annotation
	 */
	public static <A extends Annotation> Remove<A> remove(Class<A> annotation) {
		return () -> annotation;
	}

	/**
	 * 
	 * @return The class hat belongs to the annotation
	 */
	Class<A> annotation();

	/**
	 * Called when the annotation is missing from an field. This so far has only had
	 * a use with the Nullable rule
	 * 
	 * @param field    to be checked
	 * @param value    of the field
	 * @param name     as the JSON name of the field
	 * @param quantity of value
	 * 
	 * @return if the rule follows
	 */
	boolean followsMissing(Field field, Object value, String name, double quantity);

	/**
	 * Called when the annotation is present on a field.
	 * 
	 * @param field    to be checked
	 * @param value    of the field
	 * @param name     as the JSON name of the field
	 * @param quantity of value
	 * 
	 * @return if the rule follows
	 */
	boolean followsPresent(A rule, Field field, String name, Object value, double quantity);

	/**
	 * Called when the rule does not follow if missing
	 * 
	 * @param field    that was checked
	 * @param value    of the field
	 * @param name     as the JSON name of the field
	 * @param quantity of value
	 */
	void throwExceptionMissing(Field field, Object value, String name, double quantity);
	/**
	 * Called when the rule does not follow if present
	 * 
	 * @param field    that was checked
	 * @param value    of the field
	 * @param name     as the JSON name of the field
	 * @param quantity of value
	 */
	void throwExceptionPresent(A rule, Field field, String name, Object value, double quantity);
}
