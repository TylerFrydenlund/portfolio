package biz.shark.impl.quantifiers;

import biz.shark.api.Quantifier;

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
