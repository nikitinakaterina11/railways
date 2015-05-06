package nz.co.gofetch.railways.model;

import java.util.HashSet;

import nz.co.gofetch.railways.exception.RailwayException;

/**
 * railway graph configuration class
 *
 */
public class Railway {
	private HashSet<Station> stations;
	private HashSet<Route> routes;

	public HashSet<Station> getStations() {
		return stations;
	}

	public void setStations(HashSet<Station> stations) {
		this.stations = stations;
	}

	public void addStation(Station station) {
		this.stations.add(station);
	}

	/**
	 * find railway station by id
	 * 
	 * @param id
	 * @return station
	 * @throws RailwayException
	 *             if station does not exist
	 */
	public Station getStation(String id) throws RailwayException {
		if (getStations() != null) {
			for (Station station : getStations()) {
				if (station.getId().equals(id)) {
					return station;
				}
			}
		}
		throw new RailwayException("Station " + id + " does not exist!");
	}

	/**
	 * populates routes field - used for UI graph representation only
	 * 
	 */
	public void populateAllRoutes() {
		routes = new HashSet<Route>();
		// loop through all stations
		for (Station station : stations) {
			if (station.getRoutes() != null) {
				for (Route route : station.getRoutes()) {

					// only copy id and coordinates
					route.setFromStation(station.shallowCopy());
					Station toStation = getStation(route.getToStation().getId());
					// only copy id and coordinates

					route.setToStation(toStation.shallowCopy());
				}
				// add all station routes to the result list
				routes.addAll(station.getRoutes());
			}
		}
	}

	/**
	 * remove railway station by id
	 * 
	 * @param id station id
	 */
	public void removeStation(String id) throws RailwayException {
		Station station = getStation(id);
		stations.remove(station);

		// remove all links to station
		for (Station otherStation : getStations()) {
			otherStation.removeRoute(id);
		}
	}

	/**
	 * clear stations after Dijkstra run
	 */
	public void clearPaths() {
		for (Station station : getStations()) {
			station.setPrevious(null);
			station.setDistance(Integer.MAX_VALUE);
		}
	}

	public HashSet<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(HashSet<Route> routes) {
		this.routes = routes;
	}
}
