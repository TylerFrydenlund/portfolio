package biz.shark.api.rule.quantity;

import java.lang.reflect.Field;

import biz.shark.api.rule.Remove;
import biz.shark.api.rule.Rule;

public class QuantitiesRule implements Rule<Quantities> {

	public static QuantitiesRule create() {
		return new QuantitiesRule();
	}

	public static Remove<Quantities> remove() {
		return Rule.remove(Quantities.class);
	}

	private QuantitiesRule() {

	}

	@Override
	public Class<Quantities> annotation() {
		return Quantities.class;
	}

	@Override
	public boolean followsMissing(Field field, Object value, String name, double quantity) {
		return true;
	}

	@Override
	public boolean followsPresent(Quantities rule, Field field, String name, Object value, double quantity) {

		for (double q : rule.quantities()) {

			if (q == quantity) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void throwExceptionMissing(Field field, Object value, String name, double quantity) {
		return;
	}

	@Override
	public void throwExceptionPresent(Quantities rule, Field field, String name, Object value, double quantity) {
		// TODO Auto-generated method stub

	}

}
