package nz.co.gofetch.railways.algorithm.test;

import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.model.Railway;
import nz.co.gofetch.railways.model.Station;

import org.junit.Test;

public class InvalidInitDataTest {

	@Test(expected = RailwayException.class)
	public void testInvalidStation() throws RailwayException {
		new Station(null);
	}

	@Test(expected = RailwayException.class)
	public void testInvalidStation2() throws RailwayException {
		new Station("");
	}

	@Test(expected = RailwayException.class)
	public void testInvalidStationLink() throws RailwayException {
		Railway railway = new Railway();
		Station a = new Station("A");
		a.addRoute(railway.getStation("B"), 9);
	}

}
