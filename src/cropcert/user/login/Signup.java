package cropcert.user.login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	@Produces
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFarmer(String jsonString) {
		return farmerEndPoint.save(jsonString);
	}
	
	@Path("cc")
	@POST
	@Produces
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCCPerson(String jsonString) {
		return ccPersonEndPoint.save(jsonString);
	}
	
	@Path("admin")
	@POST
	@Produces
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAdmin(String jsonString) {
		return adminEndPoint.save(jsonString);
	}
}
