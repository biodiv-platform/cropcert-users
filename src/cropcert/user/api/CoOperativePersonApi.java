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

import cropcert.user.model.CoOperativePerson;
import cropcert.user.service.CoOperativePersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Path("coUser")
@Api("Co operative person")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
public class CoOperativePersonApi{

	private CoOperativePersonService coPersonService;
	
	@Inject
	public CoOperativePersonApi(CoOperativePersonService farmerService) {
		this.coPersonService = farmerService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get co-operative person by id",
			response = CoOperativePerson.class)
	public Response find(@PathParam("id") Long id) {
		CoOperativePerson ccPerson = coPersonService.findById(id);
		if(ccPerson==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(ccPerson).build();
	}
		
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get all the co-operatvie persons",
			response = CoOperativePerson.class,
			responseContainer = "List")
	public Response findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<CoOperativePerson> coPersons;
		if(limit==-1 || offset ==-1)
			coPersons = coPersonService.findAll();
		else
			coPersons = coPersonService.findAll(limit, offset);
		return Response.ok().entity(coPersons).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the co operative person",
			response = CoOperativePerson.class)
	public Response save(String  jsonString) {
		try {
			CoOperativePerson coPerson = coPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(coPerson).build();
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
			value = "Delete the factory person",
			response = CoOperativePerson.class)
	public Response delete(@PathParam("id") Long id) {
		CoOperativePerson ccPerson = coPersonService.delete(id);
		return Response.status(Status.ACCEPTED).entity(ccPerson).build();
	}

}
