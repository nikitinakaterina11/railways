package nz.co.gofetch.railways.algorithm;

import java.util.LinkedList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.exception.UserInputException;
import nz.co.gofetch.railways.model.Journey;
import nz.co.gofetch.railways.model.JourneyOptions;
import nz.co.gofetch.railways.model.Railway;
import nz.co.gofetch.railways.model.Route;
import nz.co.gofetch.railways.model.Station;
import nz.co.gofetch.railways.validator.DataValidator;

/**
 * this class provides methods to calculate all possible journey options between
 * two stations
 *
 */
public class JourneyOptionsPlanner {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JourneyOptionsPlanner.class);
	/**
	 * calculates all possible between two stations, e.g. A to C
	 * 
	 * @param railway
	 *            configuration
	 * @param start
	 *            station id
	 * @param end
	 *            station id
	 * @param stops
	 *            - maximum number of stops allowed in a route - optional
	 * @param exact
	 *            - optional flag to indicate if number of stops allowed in a
	 *            route is strict or not - if true - only routes with specified
	 *            number of stops will be returned, if null/false - all routes
	 *            up to the specified number of stops will be returned
	 * @return journey options - all possible route options between start and
	 *         end
	 * @throws UserInputException
	 *             - when search conditions are not valid/empty
	 * @throws RailwayException
	 *             - railway is not configured or start/end stations do not
	 *             exist on the railway
	 */
	public static JourneyOptions journeyOptions(Railway railway, String start,
			String end, Integer stops, Boolean exact) throws RailwayException,
			UserInputException {
		// validate railway configuration
		DataValidator.validateRailway(railway);

		// validate specified search criteria
		DataValidator.validateCriteria(start, end);
		DataValidator.validateCriteriaStops(stops);

		// find options using Breadth First Search algorithm
		JourneyOptions journeys = new JourneyOptions();
		LinkedList<Station> visited = new LinkedList<Station>();
		visited.add(railway.getStation(start));
		journeys = breadthFirstAlg(railway, visited, end, journeys);

		// filter out journeys that do match a maximum number of stops
		// conditions
		return filterJourneys(stops, exact, journeys);
	}

	/**
	 * Breadth First Search algorithm implementation using recursion
	 * 
	 * @param raiway
	 *            configuration
	 * @param visited
	 *            - list of visited stations
	 * @param end
	 *            - end station
	 * @param journeys
	 *            - result list of route options where results are added as they
	 *            are found
	 * @return journey options - list of route options between start and end
	 */
	private static JourneyOptions breadthFirstAlg(Railway railway,
			LinkedList<Station> visited, String end, JourneyOptions journeys) {

		// get last station from visited list and examine its links
		Set<Route> neighbours = visited.getLast().getRoutes();
		if (neighbours != null) {
			for (Route neighbour : neighbours) {
				// get adjacent station
				Station station = neighbour.getToStation();
				if (visited.contains(station)) {
					continue; // skip if adjacent station is visited
				}
				// if adjacent is end station - this is a route
				if (station.getId().equals(end)) {
					visited.add(station);
					// create new journey and add to list
					Journey journey = createJourney(visited);
					journeys.addJourney(journey);
					visited.removeLast();
					break;
				}
			}
			// recursion needs to come after visiting links
			for (Route neighbour : neighbours) {
				// get adjacent station
				Station station = neighbour.getToStation();
				// skip if adjacent station is visited or end
				if (visited.contains(station) || station.getId().equals(end)) {
					continue;
				}
				// add adjacent station to visited
				visited.addLast(railway.getStation(station.getId()));
				// execute algorithm for adjacent station
				breadthFirstAlg(railway, visited, end, journeys);
				visited.removeLast();
			}
		}
		return journeys;
	}

	/**
	 * creates new Journey object from list of visited links
	 * 
	 * @param visited
	 *            - list of nodes to be include into a journey
	 * @return journey - journey that contains all currently visited nodes
	 */
	private static Journey createJourney(LinkedList<Station> visited) {
		Journey journey = new Journey();
		for (Station station : visited) {
			journey.addStation(station);
		}
		LOGGER.debug("Journey: {} ", journey);
		return journey;
	}

	/**
	 * filters out invalid route options based on stops number criteria
	 * 
	 * @return journey options - all possible route options between start and
	 *         end
	 * @param stops
	 *            - maximum number of stops allowed in a route - optional
	 * @param exact
	 *            - optional flag to indicate if number of stops allowed in a
	 *            route is strict or not - if true - only routes with specified
	 *            number of stops will be returned, if null/false - all routes
	 *            up to the specified number of stops will be returned
	 * @return journey options - filtered route options based on stops number
	 *         criteria
	 */
	private static JourneyOptions filterJourneys(Integer stops, Boolean exact,
			JourneyOptions journeyOptions) {
		JourneyOptions validJourneys = new JourneyOptions();
		if (stops != null) {
			for (Journey journey : journeyOptions.getJourneys()) {
				if (journey.getStations().size() == stops) {
					validJourneys.addJourney(journey);
				}
				if ((exact == null || !exact)
						&& journey.getStations().size() < stops) {
					validJourneys.addJourney(journey);
				}
			}
		} else {
			validJourneys = journeyOptions;
		}
		return validJourneys;
	}
}
