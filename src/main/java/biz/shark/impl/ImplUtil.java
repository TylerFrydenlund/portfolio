package biz.shark.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.squareup.moshi.Json;

import biz.shark.Util;
import biz.shark.api.Handler;
import biz.shark.api.Quantifier;
import biz.shark.api.rule.Rule;
import biz.shark.api.rule.limit.LimitRule;
import biz.shark.api.rule.nullable.NullableRule;
import biz.shark.api.rule.quantity.QuantitiesRule;
import biz.shark.impl.quantifiers.ArrayQuantifier;
import biz.shark.impl.quantifiers.CollectionQuantifier;
import biz.shark.impl.quantifiers.MapQuantifier;
import biz.shark.impl.quantifiers.NumberQuantifier;
import biz.shark.impl.quantifiers.StringQuantifier;

class ImplUtil {

	public static final List<Quantifier> DEFAULT_QUANTIFIERS = List.of(new ArrayQuantifier(),
			new CollectionQuantifier(), new MapQuantifier(), new NumberQuantifier(), new StringQuantifier());

	public static final List<Rule<?>> DEFAULT_RULES = List.of(LimitRule.create(), NullableRule.create(),
			QuantitiesRule.create());

	public static Map<String, HandlerGroup> groupHandlers(List<Handler<?, ?>> handlers) {

		Map<String, HandlerGroup> groups = new HashMap<>();
		for (Handler<?, ?> h : handlers) {

			HandlerImpl<?, ?> handler = new HandlerImpl<>(h);

			groups.putIfAbsent(handler.getPath(), new HandlerGroup());

			HandlerGroup group = groups.get(handler.getPath());

			group.methods.put(handler.getMethod(), handler);
		}

		return groups;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void checkRules(Map<Class<? extends Annotation>, Rule<?>> rules, List<Quantifier> quantifiers,
			Object request) throws IllegalArgumentException, IllegalAccessException {

		rules = new HashMap<>(rules);

		for (Field field : request.getClass().getDeclaredFields()) {

			String name = getName(field);
			Object value = field.get(request);

			double quantity = Util.objectToValue(quantifiers, value);

			Annotation[] annotations = field.getAnnotations();

			for (Annotation annotation : annotations) {
				Rule rule = rules.get(annotation.annotationType());

				if (rule != null) {

					boolean follows = rule.followsPresent(annotation, field, name, annotations, quantity);

					if (!follows) {
						rule.throwExceptionPresent(annotation, field, name, annotations, quantity);
					}
				}
			}

			for (Rule rule : rules.values()) {

				boolean follows = rule.followsMissing(field, annotations, name, quantity);

				if (!follows) {
					rule.throwExceptionMissing(field, annotations, name, quantity);
				}

			}
		}
	}

	public static String getName(Field field) {

		Json json = field.getAnnotation(Json.class);

		return json == null ? field.getName() : json.name();

	}

}
