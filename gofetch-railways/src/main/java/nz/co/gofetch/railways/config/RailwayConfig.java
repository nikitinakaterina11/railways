package nz.co.gofetch.railways.config;

import java.io.File;
import java.io.IOException;

import nz.co.gofetch.railways.model.Railway;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * provides railway configuration from a file
 *
 */
public class RailwayConfig {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RailwayConfig.class);

	private static final String RAILWAY_CONFIG_JSON_FILE = "src/main/config/railway-config.json";
	private static Railway railway;

	/**
	 * reads railway configuration from a file
	 *
	 */
	public void initRailway() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			railway = mapper.readValue(new File(RAILWAY_CONFIG_JSON_FILE),
					Railway.class);
			LOGGER.debug("Railway config : {} " , railway);
		} catch (JsonParseException e) {
			LOGGER.error("Exception parsing railway config", e);
		} catch (JsonMappingException e) {
			LOGGER.error("Exception parsing railway config", e);
		} catch (IOException e) {
			LOGGER.error("Exception reading railway config file", e);
		}
	}

	/**
	 * saves updated railway configuration into a file
	 *
	 */
	public static void updateRailway() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(RAILWAY_CONFIG_JSON_FILE), railway);
		} catch (JsonParseException e) {
			LOGGER.error("Exception parsing railway config", e);
		} catch (JsonMappingException e) {
			LOGGER.error("Exception parsing railway config", e);
		} catch (IOException e) {
			LOGGER.error("Exception reading railway config file", e);
		}
	}
	
	public static void main(String[] args) {
		getRailway();
	}
	
	/**
	 * get configuration instance
	 * @return railway
	 */
	public static Railway getRailway() {
		if (railway == null) {
			new RailwayConfig().initRailway();
		}
		return railway;
	}
}
