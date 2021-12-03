package biz.shark.impl.quantifiers;

import java.util.Collection;

import biz.shark.api.Quantifier;

public class CollectionQuantifier implements Quantifier{

	@Override
	public boolean isType(Object object) {
		return object instanceof Collection;
	}

	@Override
	public double quanity(Object object) {
		return ((Collection<?>) object).size();
	}

}
