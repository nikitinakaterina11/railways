package nz.co.gofetch.railways.algorithm;

import java.util.List;

import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.exception.UserInputException;
import nz.co.gofetch.railways.model.Railway;
import nz.co.gofetch.railways.model.Station;
import nz.co.gofetch.railways.validator.DataValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this class provides method to calculate total distance for a selected path,
 * e.g. A-D-B-F
 **
 */
public class DistanceCalculator {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DistanceCalculator.class);

	/**
	 * calculates total distance for a selected path, e.g. A-D-B-F
	 * 
	 * @param railway
	 *            configuration
	 * @param targetRoute
	 *            - ordered list of station ids to follow
	 * @return distance
	 * @throws UserInputException
	 *             - when target route is empty
	 * @throws RailwayException
	 *             - a direct link between two stations on the path does not
	 *             exist
	 */
	public static int calculateDistance(Railway railway,
			List<String> targetRoute) throws RailwayException, UserInputException {
		// validate railway has stations
		DataValidator.validateRailway(railway);

		// validate target route
		if (targetRoute == null || targetRoute.isEmpty()) {
			LOGGER.error("Target route list is empty!");
			throw new UserInputException("Target route list is empty!");
		}

		int distance = 0;

		// loop through each record on target route
		for (int stationIndex = 0; stationIndex < targetRoute.size() - 1;) {

			// find station by id
			Station currentStation = railway.getStation(targetRoute
					.get(stationIndex));

			Station nextStation = railway.getStation(targetRoute
					.get(++stationIndex));

			// if link exists
			if (currentStation.getRoutes() != null
					&& currentStation.getRouteToStation(nextStation) != null) {

				// add link distance to total distance
				distance += currentStation.getRouteToStation(nextStation)
						.getDistance();
				LOGGER.debug("Distance: {}", distance);
			} else {
				// link between stations does not exist
				RailwayException e = new RailwayException(String.format(
						"Route between %s and %s does not exist!",
						currentStation.getId(), nextStation.getId()));
				LOGGER.error(e.getMessage());
				throw e;
			}
		}
		return distance;
	}
}
