package nz.co.gofetch.railways.model;

import java.util.HashSet;
import java.util.Set;

import nz.co.gofetch.railways.exception.RailwayException;

/* represents railway station object */
public class Station implements Comparable<Station> {
	private String id;
	private int x; // used for UI graph only
	private int y; // used for UI graph only
	private Set<Route> routes;
	private transient int distance = Integer.MAX_VALUE; // init value for
														// Dijkstra
	private transient Station previous; // used in Dijkstra algorithm

	public Station() {
	}

	public Station(String id) throws RailwayException {
		super();
		if (id == null || id.isEmpty()) {
			throw new RailwayException("Station id cannot be null!");
		}
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Set<Route> raiwayRoutes) {
		this.routes = raiwayRoutes;
	}

	/**
	 * add route
	 * 
	 * @param route
	 */
	public void addRoute(Route route) {
		if (routes == null) {
			routes = new HashSet<Route>();
		}
		routes.add(route);
	}

	/**
	 * update route distance
	 * 
	 * @param route
	 * @throws RailwayException
	 *             if route does not exist
	 */
	public void updateRouteDistance(Route route) throws RailwayException {
		Route routeLink = getRouteToStation(route.getToStation().getId());
		if (routeLink == null) {
			throw new RailwayException(
					String.format("Route to %s does not exist!", route
							.getToStation().getId()));
		}
		routeLink.setDistance(route.getDistance());
	}

	/**
	 * add route
	 * 
	 * @param toStation
	 * @param distance
	 */
	public void addRoute(Station toStation, int distance) {
		addRoute(new Route(toStation, new Integer(distance)));
	}

	/**
	 * remove route to the specified station by station id
	 * 
	 * @param stationId
	 */
	public void removeRoute(String stationId) throws RailwayException {
		Route route = getRouteToStation(stationId);
		if (route != null) {
			routes.remove(route);
		}
	}

	/**
	 * find route to the specified station by station id
	 * 
	 * @param stationId
	 * @return route if exists, null if routes does not exist
	 */
	public Route getRouteToStation(String stationId) {
		if (routes != null) {
			for (Route route : routes) {
				if (stationId.equals(route.getToStation().getId())) {
					return route;
				}
			}
		}
		return null;
	}

	/**
	 * find route to the specified station
	 * 
	 * @param toStation
	 * @return route if exists, null if routes does not exist
	 */
	public Route getRouteToStation(Station toStation) {
		return getRouteToStation(toStation.getId());
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Station getPrevious() {
		return previous;
	}

	public void setPrevious(Station previous) {
		this.previous = previous;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!obj.getClass().equals(getClass()))
			return false;
		return ((Station) obj).getId().equals(getId());
	}

	/**
	 * for Dijkstra implementation
	 */
	public int compareTo(Station o) {
		return Integer.compare(distance, o.getDistance());
	}

	/**
	 * copy with routes
	 */
	public Station copy(String id) throws RailwayException {
		Station o = new Station(id);
		o.setRoutes(this.routes);
		return o;
	}

	/**
	 * copy only id and coordinated
	 * 
	 * @return station
	 *
	 */
	public Station shallowCopy() throws RailwayException {
		Station o = new Station(id);
		o.setX(x);
		o.setY(y);
		return o;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
