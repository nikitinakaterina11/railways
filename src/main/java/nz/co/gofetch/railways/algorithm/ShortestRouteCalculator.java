package nz.co.gofetch.railways.algorithm;

import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.exception.UserInputException;
import nz.co.gofetch.railways.model.Journey;
import nz.co.gofetch.railways.model.Railway;
import nz.co.gofetch.railways.model.Route;
import nz.co.gofetch.railways.model.Station;
import nz.co.gofetch.railways.validator.DataValidator;

/**
 * this class provides methods to calculate shortest path between two stations
 * e.g. A and C
 *
 */
public class ShortestRouteCalculator {
	// this sting is appended to a dummy station id
	private static final String DUMMY_STATION_ID_APPENDIX = "'";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShortestRouteCalculator.class);
	/**
	 * calculates shortest path between two stations
	 * 
	 * @param railway
	 *            configuration
	 * @param start
	 *            station id
	 * @param end
	 *            station id
	 * @return journey - shortest path between start and end stations
	 * @throws UserInputException
	 *             - when search conditions are not valid/empty
	 * @throws RailwayException
	 *             - railway is not configured or start/end stations do not
	 *             exist on the railway
	 */
	public static Journey shortestJourney(Railway railway, String start,
			String end) throws RailwayException, UserInputException {
		// validate railway config
		DataValidator.validateRailway(railway);
		// validate start and end are not null/empty
		DataValidator.validateCriteria(start, end);

		Station dummyStation = null;
		Station startStation = railway.getStation(start);

		// if start and end is the same station - we'll add a dummy station to
		// the railway before calculation start. It will be a copy of a start
		// station with the same routes but different id

		if (start.equals(end)) {
			String dummyStationId = start + DUMMY_STATION_ID_APPENDIX;
			dummyStation = startStation.copy(dummyStationId);
			end = dummyStationId;
			addDummyStationToRailway(railway, dummyStation, startStation);
		}

		// use Dijkstra algorithm for route calculation
		calculateDijkstraAlg(railway, startStation);

		Journey journey = createJourney(railway, startStation, end);
		if (dummyStation != null) {
			// remove dummy station and update result journey
			journey.setCircle();
			removeDummyStation(railway, dummyStation);
		}
		return journey;
	}

	/**
	 * adds a dummy station to the railway
	 * 
	 * @param raiway
	 *            configuration
	 * @param dummyStation
	 *            - dummyStation to be added
	 * 
	 * @param originalStation
	 *            - originalStation from where a dummy station was copied
	 *            station id
	 */
	private static void addDummyStationToRailway(Railway railway,
			Station dummyStation, Station originalStation) {
		// add to railway
		railway.addStation(dummyStation);

		// check all other stations and if they contain a link to the original
		// station then add a link to a dummy station as well
		for (Station station : railway.getStations()) {
			station.getRouteToStation(originalStation);
			Route route = station.getRouteToStation(originalStation);
			if (route != null) {
				station.getRoutes().add(
						new Route(dummyStation, route.getDistance()));
			}
		}
	}

	/**
	 * removes a dummy station from the railway
	 * 
	 * @param raiway
	 *            configuration
	 * @param dummyStation
	 *            - dummyStation to be added
	 * 
	 */
	private static void removeDummyStation(Railway railway, Station dummyStation) {
		// remove from railway
		railway.getStations().remove(dummyStation);

		// update other station links
		for (Station station : railway.getStations()) {
			if (station.getRouteToStation(dummyStation) != null) {
				station.getRoutes().remove(dummyStation);
			}
		}
	}

	/**
	 * creates a journey based on Dijkstra calculations
	 * 
	 * @param raiway
	 *            configuration
	 * @param start
	 *            station
	 * @param end
	 *            station id
	 * @throws RailwayException
	 *             - as journey is created from end station - if it doesn't end
	 *             up in a start station
	 * @return journey - shortest path between start and end stations
	 */
	private static Journey createJourney(Railway railway, Station start,
			String end) throws RailwayException {
		Journey journey = new Journey();

		// start from end
		Station currentStation = railway.getStation(end);
		journey.addStation(currentStation);
		// check previous and append to journey
		while (currentStation.getPrevious() != null) {
			journey.addStation(currentStation.getPrevious());
			currentStation = currentStation.getPrevious();
		}

		LOGGER.debug("Journey reversed: {} ", journey);
		// reverse journey
		Collections.reverse(journey.getStations());
		LOGGER.debug("Journey : {} ", journey);

		// check first station to be a start
		if (!journey.getFirstStation().equals(start)) {
			throw new RailwayException(String.format(
					"Path from %s to %s does not exist!", start.getId(), end));
		}
		return journey;
	}

	/**
	 * implementation of Dijkstra's algorithm
	 * 
	 * @param raiway
	 *            configuration
	 * @param start
	 *            station
	 * 
	 */
	private static void calculateDijkstraAlg(Railway railway, Station start) {
		// clear previous stations and station distances
		railway.clearPaths();

		start.setDistance(0);

		ArrayList<Station> stations = new ArrayList<Station>();
		stations.add(start);

		Station currentStation = null;
		while (!stations.isEmpty()) {

			Collections.sort(stations);
			currentStation = stations.get(0); // station with shortest distance
												// (first iteration
			// will return source)
			stations.remove(currentStation);
			if (currentStation.getDistance() == Integer.MAX_VALUE)
				break; // current and any other remaining stations are
						// unreachable

			// look at distances to each neighbor
			if (currentStation.getRoutes() != null) {
				for (Route route : currentStation.getRoutes()) {
					// adjacent station
					Station neighbour = railway.getStation(route.getToStation()
							.getId());
					final int alternateDist = currentStation.getDistance()
							+ route.getDistance();
					if (alternateDist < neighbour.getDistance()) { // shorter
																	// path
																	// to
						// neighbor found
						stations.remove(neighbour);
						neighbour.setDistance(alternateDist);
						neighbour.setPrevious(currentStation);
						stations.add(neighbour);
					}
				}
			}
		}
	}

}
