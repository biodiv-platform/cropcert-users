package cropcert.user.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;

import com.google.inject.Inject;

import cropcert.user.filter.Permissions;
import cropcert.user.filter.TokenAndUserAuthenticated;
import cropcert.user.model.ICSManager;
import cropcert.user.model.UnionPerson;
import cropcert.user.service.ICSManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("icsManager")
@Api("ICSManager")
public class ICSManagerApi {

	private ICSManagerService icsManagerService;

	@Inject
	public ICSManagerApi(ICSManagerService icsManagerService) {
		this.icsManagerService = icsManagerService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the ICS Manager by id", response = UnionPerson.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		ICSManager icsManager = icsManagerService.findById(id);
		if (icsManager == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(icsManager).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the ics manager", response = UnionPerson.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		ICSManager icsManager;
		try {
			icsManager = icsManagerService.save(jsonString);
			return Response.status(Status.CREATED).entity(icsManager).build();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete the ICS Manger by id", response = UnionPerson.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		ICSManager icsManager = icsManagerService.delete(id);
		return Response.status(Status.ACCEPTED).entity(icsManager).build();
	}
}
