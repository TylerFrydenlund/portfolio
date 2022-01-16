package biz.shark.api.rule.nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
/**
 * A rule that marks wether a field can be null or not
 * 
 *  @see NullableRule
 * @author Tyler Frydenlund
 *
 */
public @interface Nullable {

	boolean nullable() default true;

}
