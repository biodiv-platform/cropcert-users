package cropcert.user.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;

import com.google.inject.Inject;

import cropcert.user.filter.Permissions;
import cropcert.user.filter.TokenAndUserAuthenticated;
import cropcert.user.model.Farmer;
import cropcert.user.service.FarmerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("farmer")
@Api("Farmer")
public class FarmerApi {

	private FarmerService farmerService;

	@Inject
	public FarmerApi(FarmerService farmerService) {
		this.farmerService = farmerService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get farmer by id", response = Farmer.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Farmer farmer = farmerService.findById(id);
		if (farmer == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(farmer).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the farmers", response = Farmer.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Farmer> farmers;
		if (limit == -1 || offset == -1)
			farmers = farmerService.findAll();
		else
			farmers = farmerService.findAll(limit, offset);
		return Response.ok().entity(farmers).build();
	}

	@Path("collection")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of farmer by collection center", response = Farmer.class, responseContainer = "List")
	public Response getFarmerForCollectionCenter(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("ccCode") String ccCodeString,
			@DefaultValue("-1") @QueryParam("limit") String limitString,
			@DefaultValue("-1") @QueryParam("offset") String offsetString) {

		int ccCode = Integer.parseInt(ccCodeString);
		int limit = Integer.parseInt(limitString);
		int offset = Integer.parseInt(offsetString);

		List<Farmer> farmers = farmerService.getByPropertyWithCondtion("ccCode", ccCode, "=", limit, offset);
		;
		return Response.ok().entity(farmers).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the farmer", response = Farmer.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		Farmer farmer;
		try {
			farmer = farmerService.save(jsonString);
			return Response.status(Status.CREATED).entity(farmer).build();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete the farmer by id", response = Farmer.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@PathParam("id") Long id) {
		Farmer farmer = farmerService.delete(id);
		return Response.status(Status.ACCEPTED).entity(farmer).build();
	}
}
