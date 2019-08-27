package cropcert.user.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;

import cropcert.user.filter.Permissions;
import cropcert.user.filter.TokenAndUserAuthenticated;
import cropcert.user.model.CollectionCenterPerson;
import cropcert.user.model.User;
import cropcert.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("user")
@Api("User")
public class UserApi {

	private UserService userService;

	@Inject
	public UserApi(UserService userService) {
		this.userService = userService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the user by id", response = User.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		User user = userService.findById(id);
		return Response.status(Status.CREATED).entity(user).build();
	}

	@GET
	@Path("me")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@ApiOperation(value = "Get the current user", response = Map.class)
	public Response getUser(@Context HttpServletRequest request) {
		Map<String, Object> myData = userService.getMyData(request);
		return Response.ok().entity(myData).build();
	}

	@Path("email/{email}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the user by email-id", response = User.class)
	public Response getByEmail(@Context HttpServletRequest request,
			@DefaultValue("") @PathParam("email") String email) {
		User user = userService.getByEmail(email);
		return Response.ok().entity(user).build();
	}

	@Path("userName/{userName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the user by userName", response = User.class)
	public Response getByUserName(@Context HttpServletRequest request,
			@DefaultValue("") @PathParam("userName") String userName) {
		User user = userService.getByUserName(userName);
		return Response.ok().entity(user).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the user", response = User.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		User user;
		try {
			user = userService.save(jsonString);
			return Response.status(Status.CREATED).entity(user).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}
	
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete the user by id", response = CollectionCenterPerson.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		User user = userService.delete(id);
		return Response.status(Status.ACCEPTED).entity(user).build();
	}
}
