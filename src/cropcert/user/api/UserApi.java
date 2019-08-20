package cropcert.user.api;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;

import cropcert.user.model.User;
import cropcert.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("user")
@Api("User")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
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
	@ApiOperation(
			value = "Get the user by id",
			response = User.class)
	public Response find(@PathParam("id") Long id) {
		User user = userService.findById(id);
		return Response.status(Status.CREATED).entity(user).build();
	}
	
	@Path("email/{email}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get the user by email-id",
			response = User.class)
	public Response getByEmail(@DefaultValue("") @PathParam("email") String email) { 
		User user = userService.findByPropertyWithCondtion("email", email, "=");
		return Response.ok().entity(user).build();
	}
	
	@Path("userName/{userName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get the user by userName",
			response = User.class)
	public Response getByUserName(@DefaultValue("") @PathParam("userName") String userName) { 
		User user = userService.findByPropertyWithCondtion("userName", userName, "=");
		return Response.ok().entity(user).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the user",
			response = User.class)
	public Response save(String  jsonString) {
		try {
			User user = userService.save(jsonString);
			return Response.status(Status.CREATED).entity(user).build();
		} catch(ConstraintViolationException e) {
			return Response.status(Status.CONFLICT).tag("Dublicate key").build();
		}
		catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
