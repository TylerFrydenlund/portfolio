package biz.shark.impl.quantifiers;

import java.lang.reflect.Array;

import biz.shark.api.Quantifier;

public final class ArrayQuantifier implements Quantifier {

	@Override
	public boolean isType(Object object) {
		return object.getClass().isArray();
	}

	@Override
	public double quanity(Object object) {
		return Array.getLength(object);
	}

}
