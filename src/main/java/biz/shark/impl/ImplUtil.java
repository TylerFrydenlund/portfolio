package biz.shark.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
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
import biz.shark.api.rule.value.ValueRule;
import biz.shark.impl.quantifiers.ArrayQuantifier;
import biz.shark.impl.quantifiers.CollectionQuantifier;
import biz.shark.impl.quantifiers.MapQuantifier;
import biz.shark.impl.quantifiers.NumberQuantifier;
import biz.shark.impl.quantifiers.StringQuantifier;

public class ImplUtil {

	public static final List<Quantifier> DEFAULT_QUANTIFIERS = List.of(new ArrayQuantifier(),
			new CollectionQuantifier(), new MapQuantifier(), new NumberQuantifier(), new StringQuantifier());

	public static final List<Rule<?>> DEFAULT_RULES = List.of(LimitRule.create(), NullableRule.create(),
			ValueRule.create());

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void checkRules(Map rules, List<Quantifier> quantifiers, Object request)
			throws IllegalArgumentException, IllegalAccessException {

		rules = new HashMap<>(rules);

		for (Field field : request.getClass().getDeclaredFields()) {

			field.setAccessible(true);

			String name = getName(field);
			Object value = field.get(request);

			double quantity = Util.objectToValue(quantifiers, value);


			Annotation[] annotations = field.getAnnotations();

			for (Annotation annotation : annotations) {
				Rule rule = (Rule) rules.get(annotation.annotationType());

				if (rule != null) {

					boolean follows = rule.followsPresent(annotation, field, name, request, quantity);

					if (!follows) {
						rule.throwExceptionPresent(annotation, field, name, request, quantity);
					}
				}
			}

			for (Rule rule : ((Collection<Rule>) rules.values())) {

				boolean follows = rule.followsMissing(field, request, name, quantity);
				System.out.println(follows);
				System.out.println(quantity);
				if (!follows) {
					rule.throwExceptionMissing(field, request, name, quantity);
				}

			}
		}
	}

	public static String getName(Field field) {

		Json json = field.getAnnotation(Json.class);

		return json == null ? field.getName() : json.name();

	}

}
