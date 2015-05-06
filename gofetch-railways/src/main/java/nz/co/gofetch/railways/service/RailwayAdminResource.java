package nz.co.gofetch.railways.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import nz.co.gofetch.railways.config.RailwayConfig;
import nz.co.gofetch.railways.exception.RailwayException;
import nz.co.gofetch.railways.model.Railway;
import nz.co.gofetch.railways.model.Route;
import nz.co.gofetch.railways.model.Station;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST resource that contains operations of retrieving and
 * maintaining railway configuration
 *
 */

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class RailwayAdminResource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RailwayAdminResource.class);

	/**
	 * get all railway config - used for UI graph
	 * 
	 * @return railway
	 * @throws RailwayException
	 */
	@GET
	@Path("railway-config")
	public Railway getAllRailway() {
		Railway railway = getRailwayConfig();
		railway.populateAllRoutes();
		return railway;
	}

	/**
	 * add new Station
	 * @throws RailwayException
	 */
	// TODO this method may need to change input parameters - not finished
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("station/add")
	public Response addStation(Station station) throws RailwayException {
		Railway railway = getRailwayConfig();
		railway.addStation(station);
		RailwayConfig.updateRailway();
		return Response.ok().build();
	}

	/**
	 * remove Station
	 * @throws RailwayException
	 */
	@DELETE
	@Path("station/remove/{id}")
	public Response removeStation(@PathParam("id") String id) throws RailwayException {
		try {
			getRailwayConfig().removeStation(id);
		} catch (RailwayException e) {
			LOGGER.error("Exception when removing station", e);
			throw new WebApplicationException(e, Status.BAD_REQUEST);
		}
		RailwayConfig.updateRailway();
		return Response.ok().build();
	}

	/**
	 * add new route
	 * @throws RailwayException
	 */
	// TODO this method may need to change input parameters - not finished
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("routes/add/{id}")
	public Response addRouteForStation(@PathParam("id") String stationId, Route route) {
		try {
			getRailwayConfig().getStation(stationId).addRoute(route);
		} catch (RailwayException e) {
			LOGGER.error("Exception when adding station", e);
			throw new WebApplicationException(e, Status.BAD_REQUEST);
		}
		RailwayConfig.updateRailway();
		return Response.ok().build();
	}

	/**
	 * update route distance
	 * @throws RailwayException
	 */
	// TODO this method may need to change input parameters - not finished
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("routes/update-distance/{idStation}")
	public Response updateRouteForStation(
			@PathParam("idStation") String stationId, Route route) throws RailwayException {
		try {
			getRailwayConfig().getStation(stationId).updateRouteDistance(route);
		} catch (RailwayException e) {
			LOGGER.error("Exception when updating route distance ", e);
			throw new WebApplicationException(e, Status.BAD_REQUEST);
		}
		RailwayConfig.updateRailway();
		return Response.ok().build();
	}

	/**
	 * delete route from station
	 * @return response 200 OK if success
	 * @throws RailwayException
	 * 
	 */
	// TODO this method may need to change input parameters - not finished
	@DELETE
	@Path("routes/remove/{idStation}/{idRoute}")
	public Response removeRouteForStation(
			@PathParam("idStation") String stationId,
			@PathParam("idRoute") String routeId) throws RailwayException {
		try {
			getRailwayConfig().getStation(stationId).removeRoute(routeId);
		} catch (RailwayException e) {
			LOGGER.error("Exception when removing station route ", e);
			throw new WebApplicationException(e, Status.BAD_REQUEST);
		}
		RailwayConfig.updateRailway();
		return Response.ok().build();
	}

	/**
	 * retrieves railway config
	 * 
	 * @return railway
	 * @throws RailwayException
	 */
	private Railway getRailwayConfig() throws RailwayException{
		Railway railway = RailwayConfig.getRailway();
		if (railway == null) {
			LOGGER.error("Exception when retrieving railway configuration");
			throw new RailwayException(
					"Railway system is currently unavailable",
					Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		return railway;
	}
}
