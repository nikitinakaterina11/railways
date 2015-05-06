package nz.co.gofetch.railways.algorithm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import nz.co.gofetch.railways.algorithm.DistanceCalculator;
import nz.co.gofetch.railways.data.RailwayTestDataProvider;
import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.model.Railway;

public class DistanceCalculatorTest {
	private static Railway railway;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		railway = RailwayTestDataProvider.initRailway();
	}

	@Test
	public void testCalculateDistance1() throws RailwayException {
		List<String> stations = new ArrayList<String>();
		stations.add("A");
		stations.add("B");
		stations.add("D");
		stations.add("E");
		stations.add("F");
		int distance = DistanceCalculator.calculateDistance(railway, stations);
		Assert.assertEquals(37, distance);
	}

	@Test
	public void testCalculateDistance2() throws RailwayException {
		List<String> stations = new ArrayList<String>();
		stations.add("B");
		stations.add("C");
		stations.add("D");
		stations.add("E");
		int distance = DistanceCalculator.calculateDistance(railway, stations);
		Assert.assertEquals(17, distance);
	}

	@Test
	public void tesCalculateDistance3() throws RailwayException {
		List<String> stations = new ArrayList<String>();
		stations.add("A");
		stations.add("B");
		stations.add("I");
		stations.add("H");
		stations.add("A");
		stations.add("B");
		int distance = DistanceCalculator.calculateDistance(railway, stations);
		Assert.assertEquals(64, distance);
	}

	@Test
	public void testCalculateDistance4() throws RailwayException {
		List<String> stations = new ArrayList<String>();
		stations.add("I");
		stations.add("H");
		stations.add("G");
		stations.add("F");
		stations.add("A");
		int distance = DistanceCalculator.calculateDistance(railway, stations);
		Assert.assertEquals(43, distance);
	}

	@Test
	public void testCalculateDistance5() throws RailwayException {
		List<String> stations = new ArrayList<String>();
		stations.add("A");
		stations.add("E");
		stations.add("F");
		stations.add("A");
		stations.add("G");
		int distance = DistanceCalculator.calculateDistance(railway, stations);
		Assert.assertEquals(46, distance);
	}

	@Test
	public void testCalculateDistance6() throws RailwayException {
		List<String> stations = new ArrayList<String>();
		stations.add("F");
		stations.add("A");
		int distance = DistanceCalculator.calculateDistance(railway, stations);
		Assert.assertEquals(5, distance);
	}

	@Test(expected = RailwayException.class)
	public void testCalculateDistance7() throws RailwayException {
		List<String> stations = new ArrayList<String>();
		stations.add("A");
		stations.add("A");
		int distance = DistanceCalculator.calculateDistance(railway, stations);
		Assert.assertEquals(0, distance);
	}

	@Test(expected = RailwayException.class)
	public void testCalculateDistanceInvalid1() throws RailwayException {
		List<String> stations = new ArrayList<String>();
		stations.add("A");
		stations.add("F");
		stations.add("G");
		DistanceCalculator.calculateDistance(railway, stations);
	}
	
	@Test(expected = RailwayException.class)
	public void tesCalculateDistanceInvalid2() throws RailwayException {
		List<String> stations = new ArrayList<String>();
		stations.add("B");
		stations.add("C");
		stations.add("D");
		stations.add("A");
		DistanceCalculator.calculateDistance(railway, stations);
	}
}
