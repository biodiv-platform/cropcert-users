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

import cropcert.user.model.Admin;
import cropcert.user.model.CollectionCenterPerson;
import cropcert.user.model.Farmer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("signup")
@Api("Signup")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
public class SignupApi {

	@Inject FarmerApi farmerApi;
	
	@Inject CollectionCenterPersonApi ccPersonApi;
	
	@Inject AdminApi adminApi;
	
	@Path("farmer")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the farmer",
			response = Farmer.class)
	public Response addFarmer(String jsonString) {
		return farmerApi.save(jsonString);
	}
	
	@Path("cc")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the cc person",
			response = CollectionCenterPerson.class)
	public Response addCCPerson(String jsonString) {
		return ccPersonApi.save(jsonString);
	}
	
	@Path("admin")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the admin",
			response = Admin.class)
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
	@ApiOperation(
			value = "Delete the farmer",
			response = Farmer.class)
	public Response deleteFarmer(@PathParam("id") Long id) {
		return farmerApi.delete(id);
	}
	
	@Path("cc/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(
			value = "Delete the farmer",
			response = CollectionCenterPerson.class)
	public Response deleteManager(@PathParam("id") Long id) {
		return ccPersonApi.delete(id);
	}
	
	@Path("admin/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(
			value = "Delete the admin",
			response = Admin.class)
	public Response deleteAdmin(@PathParam("id") Long id) {
		return adminApi.delete(id);
	}
}
