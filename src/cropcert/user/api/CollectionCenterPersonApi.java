package cropcert.user.api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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

import cropcert.user.model.CollectionCenterPerson;
import cropcert.user.service.CollectionCenterPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Path("ccUser")
@Api("CC User")
public class CollectionCenterPersonApi{

	private CollectionCenterPersonService ccPersonService;
	
	@Inject
	public CollectionCenterPersonApi(CollectionCenterPersonService farmerService) {
		this.ccPersonService = farmerService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get cc person by id",
			response = CollectionCenterPerson.class)
	public Response find(@PathParam("id") Long id) {
		CollectionCenterPerson ccPerson = ccPersonService.findById(id);
		if(ccPerson==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(ccPerson).build();
	}
		
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get all the cc persons",
			response = List.class)
	public List<CollectionCenterPerson> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return ccPersonService.findAll();
		else
			return ccPersonService.findAll(limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the cc person",
			response = CollectionCenterPerson.class)
	public Response save(String  jsonString) {
		try {
			CollectionCenterPerson ccPerson = ccPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(ccPerson).build();
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
			value = "Delete the cc person by id",
			response = CollectionCenterPerson.class)
	public Response delete(@PathParam("id") Long id) {
		CollectionCenterPerson ccPerson = ccPersonService.delete(id);
		return Response.status(Status.ACCEPTED).entity(ccPerson).build();
	}

}
