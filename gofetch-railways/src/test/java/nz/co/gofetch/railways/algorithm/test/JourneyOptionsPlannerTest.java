package nz.co.gofetch.railways.algorithm.test;

import java.util.List;

import nz.co.gofetch.railways.algorithm.JourneyOptionsPlanner;
import nz.co.gofetch.railways.data.RailwayTestDataProvider;
import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.exception.UserInputException;
import nz.co.gofetch.railways.model.Journey;
import nz.co.gofetch.railways.model.JourneyOptions;
import nz.co.gofetch.railways.model.Railway;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JourneyOptionsPlannerTest {

	private static Railway railway;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		railway = RailwayTestDataProvider.initRailway();
	}

	@Test
	public void testJourneyOptions1() throws RailwayException {
		JourneyOptions journeyOptions = JourneyOptionsPlanner.journeyOptions(railway,
				"A", "C", null, null);
		List<Journey> journeys = journeyOptions.getJourneys();
		Assert.assertEquals(2, journeys.size());
		Assert.assertEquals("A", journeys.get(0).getStations().get(0).getId());
		Assert.assertEquals("B", journeys.get(0).getStations().get(1).getId());
		Assert.assertEquals("C", journeys.get(0).getStations().get(2).getId());
		
		Assert.assertEquals("A", journeys.get(1).getStations().get(0).getId());
		Assert.assertEquals("B", journeys.get(1).getStations().get(1).getId());
		Assert.assertEquals("I", journeys.get(1).getStations().get(2).getId());
		Assert.assertEquals("J", journeys.get(1).getStations().get(3).getId());
		Assert.assertEquals("C", journeys.get(1).getStations().get(4).getId());
	}

	@Test
	public void testJourneyOptions2() throws RailwayException {
		JourneyOptions journeyOptions = JourneyOptionsPlanner.journeyOptions(railway,
				"B", "F", null, null);
		List<Journey> journeys = journeyOptions.getJourneys();
		Assert.assertEquals(7, journeys.size());
	}

	@Test
	public void testJourneyOptions3() throws RailwayException {
		JourneyOptions journeyOptions = JourneyOptionsPlanner.journeyOptions(railway,
				"B", "F", new Integer(5), false);
		List<Journey> journeys = journeyOptions.getJourneys();
		Assert.assertEquals(3, journeys.size());
	}

	@Test
	public void testJourneyOptions4() throws RailwayException {
		JourneyOptions journeyOptions = JourneyOptionsPlanner.journeyOptions(railway,
				"B", "F", new Integer(4), true);
		List<Journey> journeys = journeyOptions.getJourneys();
		Assert.assertEquals(1, journeys.size());
		Assert.assertEquals("B", journeys.get(0).getStations().get(0).getId());
		Assert.assertEquals("D", journeys.get(0).getStations().get(1).getId());
		Assert.assertEquals("E", journeys.get(0).getStations().get(2).getId());
		Assert.assertEquals("F", journeys.get(0).getStations().get(3).getId());
	}

	@Test
	public void testJourneyOptions5() throws RailwayException {
		JourneyOptions journeys = JourneyOptionsPlanner.journeyOptions(railway,
				"B", "F", new Integer(3), true);
		Assert.assertEquals(0, journeys.getJourneys().size());
	}

	@Test(expected = UserInputException.class)
	public void testJourneyOptions6() throws RailwayException {
		JourneyOptionsPlanner.journeyOptions(railway, "A", "B", -5, true);
	}

}
