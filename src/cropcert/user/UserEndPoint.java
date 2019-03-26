package cropcert.user;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;

@Path("user")
public class UserEndPoint {

	
	private UserService userService;
	
	@Inject
	public UserEndPoint(UserService userService) {
		this.userService = userService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		User user = userService.findById(id);
		return Response.status(Status.CREATED).entity(user).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@Path("few")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findAll(@QueryParam("limit") int limit, @QueryParam("offset") int offset) {
		return userService.findAll(limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
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
