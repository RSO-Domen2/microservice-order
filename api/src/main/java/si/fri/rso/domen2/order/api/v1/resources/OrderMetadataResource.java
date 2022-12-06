package si.fri.rso.domen2.order.api.v1.resources;

import java.util.List;
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

import si.fri.rso.domen2.order.lib.OrderMetadata;
import si.fri.rso.domen2.order.services.beans.OrderMetadataBean;

@ApplicationScoped
@Tag(name = "Order Metadata", description = "Get, add, and edit the order metadata.")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderMetadataResource {

    private final Logger LOG = Logger.getLogger(OrderMetadataResource.class.getName());

    @Inject
    private OrderMetadataBean dmb;

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
        LOG.info(uriInfo.getRequestUri().getQuery());
        List<OrderMetadata> listDm = dmb.getOrderMetadataFilter(uriInfo);
        return Response.status(Response.Status.OK)
            .header("X-Total-Count", listDm.size())
            .entity(listDm).build();
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
        
        OrderMetadata dm = dmb.getOrderMetadata(orderId);
        if(dm == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(dm).build();
    }


    @Operation(description = "Add Order metadata.", summary = "Add metadata")
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
            OrderMetadata dm) {

        dm = dmb.createOrderMetadata(dm);
        if(dm == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        return Response.status(Response.Status.CREATED).entity(dm).build();
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
            OrderMetadata dm) {

        dm = dmb.putOrderMetadata(orderId, dm);

        if(dm == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(dm).build();
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

        boolean deleted = dmb.deleteOrderMetadata(orderId);

        if(deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
