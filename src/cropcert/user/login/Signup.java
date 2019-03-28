package cropcert.user.login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;

import cropcert.user.farmer.FarmerEndPoint;

@Path("signup")
public class Signup {

	@Inject FarmerEndPoint farmerEndPoint;
	
	@Path("farmer")
	@POST
	@Produces
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFarmer(String jsonString) {
		return farmerEndPoint.save(jsonString);
	}
}
