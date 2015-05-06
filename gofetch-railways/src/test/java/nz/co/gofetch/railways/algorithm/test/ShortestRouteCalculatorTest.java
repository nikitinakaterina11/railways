package nz.co.gofetch.railways.algorithm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import nz.co.gofetch.railways.algorithm.ShortestRouteCalculator;
import nz.co.gofetch.railways.data.RailwayTestDataProvider;
import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.exception.UserInputException;
import nz.co.gofetch.railways.model.Journey;
import nz.co.gofetch.railways.model.Railway;
import nz.co.gofetch.railways.model.Station;

public class ShortestRouteCalculatorTest {
	
	private static Railway railway;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		railway = RailwayTestDataProvider.initRailway();
	}

	@Test
	public void testShortestRoute1() throws RailwayException {
		Journey journey = ShortestRouteCalculator.shortestJourney(railway, "A",
				"B");
		List<Station> stations = new ArrayList<Station>();
		stations.add(railway.getStation("A"));
		stations.add(railway.getStation("B"));
		Assert.assertArrayEquals(stations.toArray(), journey.getStations()
				.toArray());
		Assert.assertEquals(12, journey.getDistance());
	}

	@Test
	public void testShortestRoute2() throws RailwayException {
		Journey journey = ShortestRouteCalculator.shortestJourney(railway, "A",
				"J");
		List<Station> stations = new ArrayList<Station>();
		stations.add(railway.getStation("A"));
		stations.add(railway.getStation("B"));
		stations.add(railway.getStation("I"));
		stations.add(railway.getStation("J"));
		Assert.assertArrayEquals(stations.toArray(), journey.getStations()
				.toArray());
		Assert.assertEquals(37, journey.getDistance());
	}

	@Test
	public void testShortestRoute3() throws RailwayException {
		Journey journey = ShortestRouteCalculator.shortestJourney(railway, "B",
				"F");
		List<Station> stations = new ArrayList<Station>();
		stations.add(railway.getStation("B"));
		stations.add(railway.getStation("C"));
		stations.add(railway.getStation("D"));
		stations.add(railway.getStation("E"));
		stations.add(railway.getStation("F"));
		Assert.assertArrayEquals(stations.toArray(), journey.getStations()
				.toArray());
		Assert.assertEquals(22, journey.getDistance());
	}

	@Test
	public void testShortestRoute4() throws RailwayException {
		Journey journey = ShortestRouteCalculator.shortestJourney(railway, "C",
				"A");
		List<Station> stations = new ArrayList<Station>();
		stations.add(railway.getStation("C"));
		stations.add(railway.getStation("D"));
		stations.add(railway.getStation("E"));
		stations.add(railway.getStation("F"));
		stations.add(railway.getStation("A"));
		Assert.assertArrayEquals(stations.toArray(), journey.getStations()
				.toArray());
		Assert.assertEquals(22, journey.getDistance());
	}

	@Test
	public void testShortestRoute5() throws RailwayException {
		Journey journey = ShortestRouteCalculator.shortestJourney(railway, "I",
				"F");
		List<Station> stations = new ArrayList<Station>();
		stations.add(railway.getStation("I"));
		stations.add(railway.getStation("H"));
		stations.add(railway.getStation("G"));
		stations.add(railway.getStation("F"));
		Assert.assertArrayEquals(stations.toArray(), journey.getStations()
				.toArray());
		Assert.assertEquals(38, journey.getDistance());
	}

	@Test
	public void testShortestRoute6() throws RailwayException {
		Journey journey = ShortestRouteCalculator.shortestJourney(railway, "B",
				"B");
		List<Station> stations = new ArrayList<Station>();
		stations.add(railway.getStation("B"));
		stations.add(railway.getStation("I"));
		stations.add(railway.getStation("J"));
		stations.add(railway.getStation("B"));
		Assert.assertArrayEquals(stations.toArray(), journey.getStations()
				.toArray());
		Assert.assertEquals(32, journey.getDistance());
	}

	@Test(expected = UserInputException.class)
	public void testShortestRouteInvalidCriteria() throws RailwayException {
		ShortestRouteCalculator.shortestJourney(railway, null, "B");
	}

	@Test(expected = UserInputException.class)
	public void testShortestRouteInvalidCriteria2() throws RailwayException {
		ShortestRouteCalculator.shortestJourney(railway, "A", null);
	}

}
