package biz.shark.impl.quantifiers;

import biz.shark.api.Quantifier;
/**
 * A way to quantify any numerical value in order to restrict the
 * max JSON Number value to be accepted by the system
 * 
 * @author Tyler Frydenlund
 *
 */
public final class NumberQuantifier implements Quantifier {

	@Override
	public boolean isType(Object object) {
		return object instanceof Number;
	}

	@Override
	public double quanity(Object object) {
		return ((Number) object).doubleValue();
	}

}
