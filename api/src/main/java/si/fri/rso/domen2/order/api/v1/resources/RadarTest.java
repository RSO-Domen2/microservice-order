package si.fri.rso.domen2.order.api.v1.resources;

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
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import si.fri.rso.domen2.order.lib.RadarResponseDistance;
import si.fri.rso.domen2.order.services.clients.RadarClient;

@ApplicationScoped
@Tag(name = "Order Metadata", description = "Get, add, and edit the order metadata.")
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RadarTest {

    private final Logger LOG = Logger.getLogger(OrderMetadataResource.class.getName());

    @Inject
    private RadarClient rc; // = new RadarClient();

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
        RadarResponseDistance rezultat = rc.getDistance(46.0660318, 14.3920158, 45.803643, 15.1346663);
        return Response.status(Response.Status.OK).entity(rezultat).build();
    }

    @GET
    @Path("/fallback")
    public Response testFallback() {
        RadarResponseDistance rezultat = rc.fallbackGetDistance(46.0660318, 14.3920158, 45.803643, 15.1346663);
        return Response.status(Response.Status.OK).entity(rezultat).build();
    }
}
