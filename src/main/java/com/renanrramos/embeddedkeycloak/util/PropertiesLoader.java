package com.renanrramos.embeddedkeycloak.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author renan.ramos
 *
 */
public class PropertiesLoader {

	private static final Logger LOG = LoggerFactory.getLogger(PropertiesLoader.class);

	private static final String RESOURCE_FILE_NAME = "application.properties";

	private static Properties config = new Properties();

	public static Properties loadProperties() {
		InputStream input = PropertiesLoader.class.getClassLoader()
				.getResourceAsStream(RESOURCE_FILE_NAME);
		try {
			config.load(input);
			input.close();
		} catch (IOException e) {
			LOG.info("Can't read file: {}", RESOURCE_FILE_NAME);
		}
		return config;
	}

	private PropertiesLoader() {
		// Intentionally empty
	}

	public static String getUserProperty(String propName) {
		if (config == null) {
			config = loadProperties();
		}
		return Optional.ofNullable(config.getProperty(propName)).orElse("");
	}
}
