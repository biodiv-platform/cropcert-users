package cropcert.user.login;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;

import cropcert.user.admin.AdminEndPoint;
import cropcert.user.ccperson.CCPersonEndPoint;
import cropcert.user.farmer.FarmerEndPoint;

@Path("signup")
public class Signup {

	@Inject FarmerEndPoint farmerEndPoint;
	
	@Inject CCPersonEndPoint ccPersonEndPoint;
	
	@Inject AdminEndPoint adminEndPoint;
	
	@Path("farmer")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFarmer(String jsonString) {
		return farmerEndPoint.save(jsonString);
	}
	
	@Path("cc")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCCPerson(String jsonString) {
		return ccPersonEndPoint.save(jsonString);
	}
	
	@Path("admin")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAdmin(String jsonString) {
		return adminEndPoint.save(jsonString);
	}
	
	/**
	 * This delete request kept for internal purpose only
	 */
	@Path("farmer/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteFarmer(@PathParam("id") Long id) {
		return farmerEndPoint.delete(id);
	}
	
	@Path("cc/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteManager(@PathParam("id") Long id) {
		return ccPersonEndPoint.delete(id);
	}
	
	@Path("admin/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteAdmin(@PathParam("id") Long id) {
		return adminEndPoint.delete(id);
	}
}
