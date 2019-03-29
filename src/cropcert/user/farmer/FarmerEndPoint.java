package cropcert.user.farmer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;

import cropcert.user.ccperson.CCPerson;
import cropcert.user.user.User;
import cropcert.user.user.UserService;
import cropcert.user.util.UserDetailUtil;


@Path("farmer")
public class FarmerEndPoint{

	private FarmerService farmerService;
	private UserService userService;

	@Inject
	public FarmerEndPoint(FarmerService farmerService, UserService userService) {
		this.farmerService = farmerService;
		this.userService   = userService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		Farmer farmer = farmerService.findById(id);
		if(farmer==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(farmer).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Farmer> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return farmerService.findAll();
		else
			return farmerService.findAll(limit, offset);
	}
	
	/*
	 * @Path("few")
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<Farmer>
	 * findAll(@QueryParam("limit") int limit, @QueryParam("offset") int offset) {
	 * return farmerService.findAll(limit, offset); }
	 */
	
	@Path("collection")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Farmer> getFarmerForCollectionCenter(@Context HttpServletRequest request, 
			@DefaultValue("-1") @QueryParam("ccCode") String ccCodeString,
			@DefaultValue("-1") @QueryParam("limit") String limitString,
			@DefaultValue("-1") @QueryParam("offset") String offsetString) {
		
		int ccCode = Integer.parseInt(ccCodeString);
		int limit  = Integer.parseInt(limitString);
		int offset = Integer.parseInt(offsetString);
		
		if (ccCode >= 0 ) 
			return farmerService.getByPropertyWithCondtion("ccCode", ccCode, "=", limit, offset);
		
		User user = UserDetailUtil.getUserDetails(request, userService);
		if (user instanceof CCPerson) {
			CCPerson ccPerson = (CCPerson) user;
			ccCode = ccPerson.getCcCode();
			return farmerService.getByPropertyWithCondtion("ccCode", ccCode, "=", limit, offset);
		}
		
		return new ArrayList<Farmer>();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(String  jsonString) {
		try {
			Farmer farmer = farmerService.save(jsonString);
			return Response.status(Status.CREATED).entity(farmer).build();
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
