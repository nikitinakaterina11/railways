package nz.co.gofetch.railways.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * represents a route from start to end station contains a list of stations to
 * be passed through including start and end
 *
 */
public class Journey {
	private List<Station> stations;

	public Journey() {
	}

	/**
	 * @return stations
	 *
	 */
	public List<Station> getStations() {
		return stations;
	}

	/**
	 * @param stations list of stations
	 *
	 */
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

	/**
	 * add new station
	 * 
	 * @param station new station
	 *
	 */
	public void addStation(Station station) {
		if (stations == null) {
			stations = new ArrayList<Station>();
		}
		if (!stations.contains(station)) {
			stations.add(station);
		}
	}

	/**
	 * get route total distance
	 * 
	 * @return distance
	 *
	 */
	@JsonProperty("distance")
	public int getDistance() {
		if (stations == null || stations.isEmpty()) {
			return 0;
		}
		// for shortest route case based on Dijkstra implementation - total
		// distance is saved i the last journey station
		return getLastStation().getDistance();
	}

	/**
	 * get last station
	 * 
	 * @return station
	 *
	 */
	public Station getLastStation() {
		if (stations == null || stations.isEmpty()) {
			return null;
		}
		return stations.get(stations.size() - 1);

	}

	/**
	 * update for loop scenario
	 * 
	 * @return station
	 *
	 */
	public Station setCircle() {
		if (stations == null || stations.isEmpty()) {
			return null;
		}
		// copy last station distance to the first station so that it is not
		// lost
		getFirstStation().setDistance(getLastStation().getDistance());
		// copy last station to first station
		return stations.set(stations.size() - 1, getFirstStation());

	}

	/**
	 * get first station
	 * 
	 * @return station
	 *
	 */
	public Station getFirstStation() {
		if (stations == null || stations.isEmpty()) {
			return null;
		}
		return stations.get(0);

	}

	/**
	 *
	 * @return string
	 *
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if (getStations() != null) {
			for (Station station : getStations()) {
				buffer.append(station.getId()).append(" -> ");
			}
			// delete last ->
			buffer = buffer.delete(buffer.lastIndexOf(" -> "),
					buffer.length() - 1);
			// shortest route case
			if (getDistance() != Integer.MAX_VALUE) {
				buffer.append(", distance=").append(getDistance());
			}
			return buffer.toString();
		}
		return "Empty Journey";
	}

}
