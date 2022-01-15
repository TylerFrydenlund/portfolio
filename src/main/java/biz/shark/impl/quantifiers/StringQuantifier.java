package biz.shark.impl.quantifiers;

import biz.shark.api.Quantifier;
/**
 * A way to quantify a string to a numerical value in order to restrict the
 * length of accept JSON strings by the system
 * 
 * @author Tyler Frydenlund
 *
 */
public final class StringQuantifier implements Quantifier {

	@Override
	public boolean isType(Object object) {
		return object instanceof String;
	}

	@Override
	public double quanity(Object object) {
		return ((String) object).length();
	}

}
