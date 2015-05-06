package nz.co.gofetch.railways.model;

/**
 * represents a link between two stations
 *
 */
public class Route {
	private Station fromStation;
	private Station toStation;
	private int distance;

	public Route() {
	}

	public Route(Station toStation, int distance) {
		super();
		this.toStation = toStation;
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

	public Station getToStation() {
		return toStation;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setToStation(Station station) {
		this.toStation = station;
	}
	
	public Station getFromStation() {
		return fromStation;
	}
	
	public void setFromStation(Station fromStation) {
		this.fromStation = fromStation;
	}

	public int compareTo(Route o) {
		return toStation.compareTo(o.getToStation());
	}
}
