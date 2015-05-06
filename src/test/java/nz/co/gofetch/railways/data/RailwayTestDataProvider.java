package nz.co.gofetch.railways.data;

import java.util.HashSet;

import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.model.Railway;
import nz.co.gofetch.railways.model.Station;

public class RailwayTestDataProvider {
	public static Railway initRailway() throws RailwayException {
		Railway railway = new Railway();
		HashSet<Station> stations = new HashSet<Station>();
		Station a = new Station("A");
		Station b = new Station("B");
		Station c = new Station("C");
		Station d = new Station("D");
		Station e = new Station("E");
		Station f = new Station("F");
		Station g = new Station("G");
		Station h = new Station("H");
		Station i = new Station("I");
		Station j = new Station("J");
		stations.add(a);
		stations.add(b);
		stations.add(c);
		stations.add(d);
		stations.add(e);
		stations.add(f);
		stations.add(g);
		stations.add(h);
		stations.add(i);
		stations.add(j);
		railway.setStations(stations);
		
		a.addRoute(b, 12);
		a.addRoute(d, 19);
		a.addRoute(e, 20);
		a.addRoute(g, 16);
		b.addRoute(c, 5);
		b.addRoute(d, 13);
		b.addRoute(i, 15);
		c.addRoute(d, 5);
		d.addRoute(e, 7);
		e.addRoute(f, 5);
		f.addRoute(a, 5);
		g.addRoute(f, 11);
		h.addRoute(a, 4);
		h.addRoute(g, 6);
		i.addRoute(h, 21);
		i.addRoute(j, 10);
		j.addRoute(b, 7);
		j.addRoute(c, 15);

		return railway;
	}

}
