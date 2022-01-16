package biz.shark.api.rule.value;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)

/**
 * A rule used on fields to mark accepted values much like an enum
 * 
 * @see ValueRule
 * @author Tyler Frydenlund
 *
 */
public @interface Values {
	double[] values();
}
