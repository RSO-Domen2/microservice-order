package si.fri.rso.domen2.order.api.v1.resources;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.kumuluz.ee.logs.cdi.Log;

import si.fri.rso.domen2.order.lib.ClientResponse;
import si.fri.rso.domen2.order.lib.DeliverymanResponse;
import si.fri.rso.domen2.order.lib.MenuResponse;
import si.fri.rso.domen2.order.lib.RadarResponseDistance;
import si.fri.rso.domen2.order.lib.RestaurantResponse;
import si.fri.rso.domen2.order.services.clients.ClientClient;
import si.fri.rso.domen2.order.services.clients.DeliverymanClient;
import si.fri.rso.domen2.order.services.clients.RadarClient;
import si.fri.rso.domen2.order.services.clients.RestaurantClient;

@Log
@ApplicationScoped
@Tag(name = "Test", description = "Test external APIs")
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExternalTest {

    private final Logger LOG = Logger.getLogger(ExternalTest.class.getName());

    @Inject
    private RadarClient radar;

    @Inject
    private DeliverymanClient dc;

    @Inject
    private RestaurantClient rc;

    @Inject
    private ClientClient cc;

    @Context
    protected UriInfo uriInfo;

    /* @GET
    public void asyncRazmerja(@Suspended final AsyncResponse asinhroniOdgovor) {
        asinhroniOdgovor.setTimeoutHandler(unused -> unused.resume(Response.status(503).entity("Operation time out.").build()));
        asinhroniOdgovor.setTimeout(10, TimeUnit.SECONDS);
        new Thread(() -> {
            RadarResponseDistance rezultat = rc.getDistance(46.0660318, 14.3920158, 45.803643, 15.1346663);
            asinhroniOdgovor.resume(rezultat);
        }).start();
    } */

    @GET
    @Path("/radar")
    public Response testRadar() {
        this.LOG.info("GET "+uriInfo.getRequestUri().toString());
        RadarResponseDistance result = radar.getDistance(46.0660318, 14.3920158, 45.803643, 15.1346663);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/radar/fallback")
    public Response testFallback() {
        this.LOG.info("GET "+uriInfo.getRequestUri().toString());
        RadarResponseDistance result = radar.fallbackGetDistance(46.0660318, 14.3920158, 45.803643, 15.1346663);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/deliveryman")
    public Response testDeliveryman() {
        this.LOG.info("GET "+uriInfo.getRequestUri().toString());
        DeliverymanResponse[] result = dc.getAllDeliveryman();
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/restaurant/{restaurantId}")
    public Response testRestaurant(
        @Parameter(description = "Restaurant metadata ID.", required = true)
        @PathParam("restaurantId") Integer restaurantId
    ) {
        this.LOG.info("GET "+uriInfo.getRequestUri().toString());
        RestaurantResponse result = rc.getRestaurant(restaurantId);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/menu/{menuId}")
    public Response testMenu(
        @Parameter(description = "Menu metadata ID.", required = true)
        @PathParam("menuId") Integer menuId
    ) {
        this.LOG.info("GET "+uriInfo.getRequestUri().toString());
        MenuResponse result = rc.getMenu(menuId);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/client/{clientId}")
    public Response testClient(
        @Parameter(description = "Client metadata ID.", required = true)
        @PathParam("clientId") Integer clientId
    ) {
        this.LOG.info("GET "+uriInfo.getRequestUri().toString());
        ClientResponse result = cc.getClient(clientId);
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
