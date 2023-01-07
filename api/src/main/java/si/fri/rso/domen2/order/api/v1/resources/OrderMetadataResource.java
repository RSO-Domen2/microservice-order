package si.fri.rso.domen2.order.api.v1.resources;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.kumuluz.ee.logs.cdi.Log;

import si.fri.rso.domen2.order.lib.OrderMetadata;
import si.fri.rso.domen2.order.services.beans.OrderMetadataBean;
import si.fri.rso.domen2.order.services.routing.Optimize;

@Log
@ApplicationScoped
@Tag(name = "Order Metadata", description = "Get, add, and edit the order metadata.")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderMetadataResource {

    private final Logger LOG = Logger.getLogger(OrderMetadataResource.class.getName());

    @Inject
    private OrderMetadataBean omb;

    @Inject
    private Optimize opti;

    @Context
    protected UriInfo uriInfo;


    @Operation(summary = "Get all metadata", description = "Get all Order metadata")
    @APIResponses({
        @APIResponse(responseCode = "200",
            description = "List of Order metadata",
            content = @Content(schema = @Schema(implementation = OrderMetadata.class, type = SchemaType.ARRAY)),
            headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
        )})
    @GET
    public Response getOrderMetadata() {
        this.LOG.info("GET "+uriInfo.getRequestUri().toString());
        List<OrderMetadata> listOm = omb.getOrderMetadataFilter(uriInfo);
        return Response.status(Response.Status.OK)
            .header("X-Total-Count", listOm.size())
            .entity(listOm).build();
    }


    @Operation(description = "Get metadata for an Order.", summary = "Get metadata")
    @APIResponses({
        @APIResponse(responseCode = "200",
            description = "Order metadata",
            content = @Content(schema = @Schema(implementation = OrderMetadata.class))),
        @APIResponse(responseCode = "404", description = "Invalid orderId")
    })
    @GET
    @Path("/{orderId}")
    public Response getOrderMetadata(
        @Parameter(description = "Order metadata ID.", required = true)
        @PathParam("orderId") Integer orderId) {
        
        this.LOG.info("GET "+uriInfo.getRequestUri().toString());
        OrderMetadata om = omb.getOrderMetadata(orderId);
        if(om == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(om).build();
    }


    /* @Operation(description = "Add Order metadata.", summary = "Add metadata")
    @APIResponses({
        @APIResponse(responseCode = "201",
            description = "Metadata successfully added.",
            content = @Content(schema = @Schema(implementation = OrderMetadata.class))),
        @APIResponse(responseCode = "406", description = "Validation error.")
    })
    @POST
    public Response createOrderMetadata(
        @RequestBody(description = "DTO object with Order metadata.",
            required = true,
            content = @Content(schema = @Schema(implementation = OrderMetadata.class)))
            OrderMetadata om) {
        
        this.LOG.info("POST "+uriInfo.getRequestUri().toString());
        om = omb.createOrderMetadata(om);
        if(om == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        return Response.status(Response.Status.CREATED).entity(om).build();
    } */

    
    @Operation(description = "Add Order metadata.", summary = "Add metadata")
    @APIResponses({
        @APIResponse(responseCode = "201",
            description = "Metadata successfully added.",
            content = @Content(schema = @Schema(implementation = OrderMetadata.class))),
        @APIResponse(responseCode = "406", description = "Validation error."),
        @APIResponse(responseCode = "503", description = "Time out.")
    })
    @POST
    public void asyncCreateOrderMetadata(
        @RequestBody(description = "DTO object with Order metadata.",
        required = true,
        content = @Content(schema = @Schema(implementation = OrderMetadata.class)))
        OrderMetadata om,
        @Suspended final AsyncResponse asyncResponse
    ) {
        this.LOG.info("POST ASYNC "+uriInfo.getRequestUri().toString());
        asyncResponse.setTimeoutHandler(unused -> {
            this.LOG.warning("POST ASYNC "+uriInfo.getRequestUri().toString()+" TIME OUT");
            unused.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).build());
        });
        asyncResponse.setTimeout(30, TimeUnit.SECONDS);
        new Thread(() -> {
            OrderMetadata updatedOM = opti.calculateBestDeliveryman(om);
            updatedOM = omb.createOrderMetadata(updatedOM);
            if(updatedOM == null) {
                asyncResponse.resume(Response.status(Response.Status.NOT_ACCEPTABLE).build());
            }
            asyncResponse.resume(Response.status(Response.Status.CREATED).entity(om).build());
        }).start();
    }


    @Operation(description = "Update Order metadata.", summary = "Update metadata")
    @APIResponses({
        @APIResponse(responseCode = "200",
            description = "Metadata successfully updated.",
            content = @Content(schema = @Schema(implementation = OrderMetadata.class))),
        @APIResponse(responseCode = "404", description = "Metadata not found."),
    })
    @PUT
    @Path("/{orderId}")
    public Response putImageMetadata(
        @Parameter(description = "Metadata ID.", required = true)
        @PathParam("orderId")
            Integer orderId,
        @RequestBody(description = "DTO object with Order metadata.",
            required = true,
            content = @Content(schema = @Schema(implementation = OrderMetadata.class)))
            OrderMetadata om
    ) {
        this.LOG.info("PUT "+uriInfo.getRequestUri().toString());

        om = omb.putOrderMetadata(orderId, om);
        if(om == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(om).build();
    }


    @Operation(description = "Delete Order metadata.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Metadata successfully deleted."),
            @APIResponse(responseCode = "404", description = "Metadata not found.")
    })
    @DELETE
    @Path("/{orderId}")
    public Response deleteImageMetadata(
        @Parameter(description = "Metadata ID.", required = true)
        @PathParam("orderId")
            Integer orderId) {
        
        this.LOG.info("DELETE "+uriInfo.getRequestUri().toString());

        boolean deleted = omb.deleteOrderMetadata(orderId);

        if(deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
