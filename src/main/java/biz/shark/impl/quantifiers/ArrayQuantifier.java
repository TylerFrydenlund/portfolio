package biz.shark.impl.quantifiers;

import java.lang.reflect.Array;

import biz.shark.api.Quantifier;

/**
 * A way to quantify a array to a numerical value in order to restrict the
 * length of accept JSON arrays by the system
 * 
 * @author Tyler Frydenlund
 *
 */
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
