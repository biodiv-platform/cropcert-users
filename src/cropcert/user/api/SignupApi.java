package cropcert.user.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;

import cropcert.user.api.AdminApi;
import cropcert.user.api.CollectionCenterPersonApi;
import cropcert.user.api.FarmerApi;
import io.swagger.annotations.Api;

@Path("signup")
@Api("Signup")
public class SignupApi {

	@Inject FarmerApi farmerApi;
	
	@Inject CollectionCenterPersonApi ccPersonApi;
	
	@Inject AdminApi adminApi;
	
	@Path("farmer")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFarmer(String jsonString) {
		return farmerApi.save(jsonString);
	}
	
	@Path("cc")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCCPerson(String jsonString) {
		return ccPersonApi.save(jsonString);
	}
	
	@Path("admin")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAdmin(String jsonString) {
		return adminApi.save(jsonString);
	}
	
	/**
	 * This delete request kept for internal purpose only
	 */
	@Path("farmer/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteFarmer(@PathParam("id") Long id) {
		return farmerApi.delete(id);
	}
	
	@Path("cc/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteManager(@PathParam("id") Long id) {
		return ccPersonApi.delete(id);
	}
	
	@Path("admin/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteAdmin(@PathParam("id") Long id) {
		return adminApi.delete(id);
	}
}
