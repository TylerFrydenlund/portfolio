package biz.shark.impl.quantifiers;

import biz.shark.api.Quantifier;

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
