package biz.shark.impl.quantifiers;

import java.util.Collection;

import biz.shark.api.Quantifier;
/**
 * A way to quantify a collection to a numerical value in order to restrict the
 * size of accept JSON collections by the system
 * 
 * @author Tyler Frydenlund
 *
 */
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
