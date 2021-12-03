package biz.shark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.SSLException;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.Validate;
import org.json.JSONObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Moshi.Builder;
import com.sun.net.httpserver.HttpExchange;

import biz.shark.api.Handler;
import biz.shark.api.Microservice;
import biz.shark.api.Quantifier;
import biz.shark.api.rule.limit.Limit;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class Util {

	public static final Map<String, Object> SERVICE_DEFAULTS = Map.of("port", 8080);

	public static Yaml createYaml() {

		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);

		return new Yaml(options);

	}

	public static void dumpDefaults(Yaml yaml, File config, Map<String, Object> defaults) throws IOException {
		if (config.exists()) {
			config.createNewFile();
			final FileWriter writer = new FileWriter(config);
			yaml.dump(defaults, writer);
		}

	}

	public static SslContext createCertificate() throws SSLException, CertificateException {

		SelfSignedCertificate ssc = new SelfSignedCertificate();

		return SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();

	}

	public static Map<String, Object> loadDefaults(Microservice microservice) {
		Logger logger = microservice.logger();

		logger.info("Loading microservice defaults...");

		Map<String, Object> defaults = microservice.configDefaults() == null ? new HashMap<>()
				: new HashMap<>(microservice.configDefaults());

		defaults.putAll(SERVICE_DEFAULTS);

		logger.info("Loading defaults complete.");

		return defaults;

	}

	public static List<Handler<?, ?>> loadHandlers(Microservice microservice) {

		Logger logger = microservice.logger();

		logger.info("Loading microservice handlers...");

		List<Handler<?, ?>> handlers = microservice.handlers() == null ? List.of()
				: new ArrayList<>(microservice.handlers());

		Validate.noNullElements(handlers, "Null handlers can not be implemented into a microservice");

		logger.info("Loading handlers complete.");

		return handlers;

	}

	public static Moshi buildMoshi(List<Object> handlers) {

		Builder builder = new Moshi.Builder();

		for (Object handler : handlers) {

			builder.add(handler);
		}

		return builder.build();
	}

	public static String errorReport(Throwable t) {

		JSONObject json = new JSONObject();

		json.put("errorType", t.getClass().getSimpleName());
		json.put("error", t.getMessage());

		return json.toString();

	}

	public static void writeResponse(HttpExchange exchange, int code, String message) throws IOException {
		byte[] bytes = message.getBytes();

		OutputStream stream = exchange.getResponseBody();
		exchange.sendResponseHeaders(code, message.length());
		stream.write(bytes);

	}

	public static Range<Double> toRange(Limit limit) {

		double lower = Math.min(limit.min(), limit.max());
		double upper = Math.max(limit.min(), limit.max());

		return Range.between(lower, upper);

	}

	public static double objectToValue(List<Quantifier> quantifiers, Object obj) {

		for (Quantifier quantifier : quantifiers) {

			if (quantifier.isType(obj)) {

				return quantifier.quanity(obj);
			}

		}

		return Double.NaN;

	}
}
