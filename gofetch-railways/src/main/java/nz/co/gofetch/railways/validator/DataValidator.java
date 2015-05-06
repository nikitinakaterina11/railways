package nz.co.gofetch.railways.validator;

import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.exception.UserInputException;
import nz.co.gofetch.railways.model.Railway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * provides methods to validate input/config data
 *
 */
public class DataValidator {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataValidator.class);

	/**
	 * validates railway is not null and has stations
	 * 
	 * @param railway
	 *            configuration
	 * @throws RailwayException
	 */
	public static void validateRailway(Railway railway) throws RailwayException {
		if (railway == null || railway.getStations() == null
				|| railway.getStations().isEmpty()) {
			LOGGER.error("Railway is empty!");
			throw new RailwayException("Railway is empty!");
		}
	}

	/**
	 * validates start and end stops are not null or empty
	 * 
	 * @param start
	 * @param end
	 * @throws UserInputException
	 */
	public static void validateCriteria(String start, String end)
			throws UserInputException {
		if (start == null || start.isEmpty()) {
			LOGGER.error("Start station is empty!");
			throw new UserInputException("Start station is empty!");
		}
		if (end == null || end.isEmpty()) {
			LOGGER.error("End station is empty!");
			throw new UserInputException("End station is empty!");
		}
	}

	/**
	 * validates number of stops is positive value
	 * 
	 * @param stops
	 * @throws UserInputException
	 */
	public static void validateCriteriaStops(Integer stops)
			throws UserInputException {
		if (stops != null && stops <= 0) {
			LOGGER.error("Stops is less or equals to zero!");
			throw new UserInputException("Stops is less or equals to zero!");
		}
	}
}
