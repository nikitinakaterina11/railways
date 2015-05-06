package nz.co.gofetch.railways.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nz.co.gofetch.railways.algorithm.DistanceCalculator;
import nz.co.gofetch.railways.algorithm.JourneyOptionsPlanner;
import nz.co.gofetch.railways.algorithm.ShortestRouteCalculator;
import nz.co.gofetch.railways.config.RailwayConfig;
import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.exception.UserInputException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST resource that contains passenger data search
 *
 */

@Path("/passenger")
@Produces(MediaType.TEXT_PLAIN)
public class RailwayPassengerResource {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RailwayPassengerResource.class);

	/**
	 * get total distance for the path
	 * 
	 * @param path - path to calculate the distance
	 * @return distance
	 * @throws UserInputException invalid user input exception
	 * @throws RailwayException railway configuration exception
	 */
	@GET
	@Path("distance")
	public String getDistance(@QueryParam("path") String path)
			throws RailwayException, UserInputException {
		if (path == null || path.isEmpty()) {
			LOGGER.error("Input is null ");
			throw new RailwayException("Path cannot be empty!");
		}
		List<String> stations = new ArrayList<String>();
		for (String station : path.split("-")) {
			stations.add(station.trim());
		}
		return String.valueOf(DistanceCalculator.calculateDistance(
				RailwayConfig.getRailway(), stations));
	}

	/**
	 * get all journey-options for start and end
	 * 
	 * @param from - from station
	 * @param to - to station
	 * @param stops - optional - number of stops allowed
	 * @param exact optional flag for exact/maximum number of stops
	 * @return string
	 * @throws UserInputException invalid user input exception
	 * @throws RailwayException railway configuration exception
	 */
	@GET
	@Path("journey-options")
	public String getJourneyOptions(@QueryParam("from") String from,
			@QueryParam("to") String to, @QueryParam("stops") Integer stops,
			@QueryParam("exact") Boolean exact) throws RailwayException,
			UserInputException {
		return JourneyOptionsPlanner.journeyOptions(RailwayConfig.getRailway(),
				from, to, stops, exact).toString();
	}

	/**
	 * get shortest journey from start to end
	 * 
	 * @param from  - start station
	 * @param to -  end station
	 * @return distance
	 * @throws UserInputException invalid user input exception
	 * @throws RailwayException railway configuration exception
	 */
	@GET
	@Path("shortest-route")
	public String getShortestRoute(@QueryParam("from") String from,
			@QueryParam("to") String to) throws RailwayException,
			UserInputException {
		return ShortestRouteCalculator.shortestJourney(
				RailwayConfig.getRailway(), from, to).toString();
	}
}
