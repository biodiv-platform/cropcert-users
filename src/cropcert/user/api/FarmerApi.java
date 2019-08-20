package cropcert.user.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import cropcert.user.model.Farmer;
import cropcert.user.model.User;
import cropcert.user.service.FarmerService;
import cropcert.user.service.UserService;
import cropcert.user.util.UserDetailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Path("farmer")
@Api("Farmer")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
public class FarmerApi{

	private FarmerService farmerService;
	private UserService userService;

	@Inject
	public FarmerApi(FarmerService farmerService, UserService userService) {
		this.farmerService = farmerService;
		this.userService   = userService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get farmer by id",
			response = Farmer.class)
	public Response find(@PathParam("id") Long id) {
		Farmer farmer = farmerService.findById(id);
		if(farmer==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(farmer).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get all the farmers",
			response = Farmer.class,
			responseContainer = "List")
	public Response findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Farmer> farmers;
		if(limit==-1 || offset ==-1)
			farmers = farmerService.findAll();
		else
			farmers = farmerService.findAll(limit, offset);
		return Response.ok().entity(farmers).build();
	}
		
	@Path("collection")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get list of farmer by collection center",
			response = Farmer.class,
			responseContainer = "List")
	public Response getFarmerForCollectionCenter(@Context HttpServletRequest request, 
			@DefaultValue("-1") @QueryParam("ccCode") String ccCodeString,
			@DefaultValue("-1") @QueryParam("limit") String limitString,
			@DefaultValue("-1") @QueryParam("offset") String offsetString) {
		
		int ccCode = Integer.parseInt(ccCodeString);
		int limit  = Integer.parseInt(limitString);
		int offset = Integer.parseInt(offsetString);
		
		List<Farmer> farmers;
		if (ccCode >= 0 ) 
			farmers = farmerService.getByPropertyWithCondtion("ccCode", ccCode, "=", limit, offset);
		else {
			User user = UserDetailUtil.getUserDetails(request, userService);
			if (user instanceof Farmer) {
				Farmer farmer = (Farmer) user;
				ccCode = farmer.getCcCode();
				farmers = farmerService.getByPropertyWithCondtion("ccCode", ccCode, "=", limit, offset);
			}
			else {
				farmers = new ArrayList<Farmer>();
			}
		}
		
		return Response.ok().entity(farmers).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the farmer",
			response = Farmer.class)
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

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(
			value = "Delete the farmer by id",
			response = Farmer.class)
	public Response delete(@PathParam("id") Long id) {
		Farmer farmer = farmerService.delete(id);
		return Response.status(Status.ACCEPTED).entity(farmer).build();
	}
}
