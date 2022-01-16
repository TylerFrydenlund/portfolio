package biz.shark.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Range;

import com.squareup.moshi.Json;

import biz.shark.Util;
import biz.shark.api.Handler;
import biz.shark.api.Quantifier;
import biz.shark.api.Rules;
import biz.shark.impl.quantifiers.ArrayQuantifier;
import biz.shark.impl.quantifiers.CollectionQuantifier;
import biz.shark.impl.quantifiers.MapQuantifier;
import biz.shark.impl.quantifiers.NumberQuantifier;
import biz.shark.impl.quantifiers.StringQuantifier;

public class ImplUtil {

	public static final List<Quantifier> DEFAULT_QUANTIFIERS = List.of(new ArrayQuantifier(),
			new CollectionQuantifier(), new MapQuantifier(), new NumberQuantifier(), new StringQuantifier());

	public static Map<String, HandlerGroup> groupHandlers(List<Handler<?, ?>> handlers) {

		Map<String, HandlerGroup> groups = new HashMap<>();
		for (Handler<?, ?> h : handlers) {

			HandlerImpl<?, ?> handler = new HandlerImpl<>(h);

			groups.putIfAbsent(handler.path(), new HandlerGroup());

			HandlerGroup group = groups.get(handler.path());

			group.methods.put(handler.method(), handler);
		}

		return groups;

	}

	public static void checkRules(List<Quantifier> quantifiers, Object request)
			throws IllegalArgumentException, IllegalAccessException {
		for (Field field : request.getClass().getDeclaredFields()) {

			field.setAccessible(true);

			Rules rules = field.getAnnotation(Rules.class);
			String name = name(field);

			boolean nullable = (rules != null && rules.nullable());
//			System.out.println(request.getClass().getSimpleName());
//			System.out.println(name + " - " + nullable);
//			System.out.println(rules != null);
//			System.out.println(rules.nullable());
//			System.out.println(rules.min());
//			System.out.println(rules.max());
			Object value = field.get(request);

			if (value == null && nullable) {
				continue;
			} else if (value == null && !nullable) {
				throw new NullPointerException(name + " was not specified. " + name + " must be specified");
			}

			if (rules == null || value == null || value instanceof Enum) {
				continue;
			}

			double quantity = Util.objectToValue(quantifiers, value);

			Range<Double> range = toRange(rules);

			if (!range.contains(quantity)) {
				throw new IndexOutOfBoundsException(name + " was out of bounds! Quantitative Value: (" + quantity
						+ ") range{min=" + range.getMinimum() + ", max=" + range.getMaximum() + "}");
			}

		}
	}

	public static Range<Double> toRange(Rules rules) {

		double lower = Math.min(rules.min(), rules.max());
		double upper = Math.max(rules.min(), rules.max());

		return Range.between(lower, upper);

	}

	public static String name(Field field) {

		Json json = field.getAnnotation(Json.class);

		return json == null ? field.getName() : json.name();

	}
}
