package cropcert.user.signup;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
	
	@GET
	public String getMessage() {
		return "hello";
	}
	
	@POST
	@Produces
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFarmer(String jsonString) {
		return farmerEndPoint.save(jsonString);
	}
}
