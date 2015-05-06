package nz.co.gofetch.railways.model;

import java.util.ArrayList;
import java.util.List;


/**
 * wrapper class for list of journeys
 *
 */
public class JourneyOptions {
	private List<Journey> journeys = new ArrayList<Journey>();

	public List<Journey> getJourneys() {
		return journeys;
	}

	public void addJourney(Journey journey) {
		journeys.add(journey);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if (!journeys.isEmpty()) {
			int index = 0;
			for (Journey journey : journeys) {
				buffer.append("Option ");
				buffer.append(++index);
				buffer.append(" : ");
				buffer.append(journey);
				buffer.append(";");
			}
		}
		else {
			return "No journeys found!";
		}
		return buffer.toString();
	}
}
