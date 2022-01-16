package biz.shark.api.rule.limit;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)

/**
 * A annotation rule used for limiting a fields quantitative value.
 * 
 * defaults{ min="Double.MIN_VALUE", max="Double.MAX_VALUE"}
 * 
 * @see LimitRule
 * @see Double
 * @author Tyler Frydenlund
 *
 */
public @interface Limit {

	double min() default Double.MIN_VALUE;

	double max() default Double.MAX_VALUE;

}
