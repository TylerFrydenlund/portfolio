package biz.shark.impl.quantifiers;

import java.util.Map;

import biz.shark.api.Quantifier;
/**
 * A way to quantify a map to a numerical value in order to restrict the
 * size of accept JSON maps by the system
 * 
 * @author Tyler Frydenlund
 *
 */
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
