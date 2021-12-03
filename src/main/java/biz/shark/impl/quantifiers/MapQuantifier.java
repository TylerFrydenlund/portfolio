package biz.shark.impl.quantifiers;

import java.util.Map;

import biz.shark.api.Quantifier;

public final class MapQuantifier implements Quantifier {

	@Override
	public boolean isType(Object object) {
		return object instanceof Map;
	}

	@Override
	public double quanity(Object object) {
		return ((Map<?, ?>) object).size();
	}

}
