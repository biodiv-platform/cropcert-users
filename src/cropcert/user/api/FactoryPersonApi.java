package cropcert.user.api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
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

import cropcert.user.model.FactoryPerson;
import cropcert.user.service.FactoryPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Path("factory")
@Api("Factory person")
public class FactoryPersonApi{

	private FactoryPersonService factoryPersonService;
	
	@Inject
	public FactoryPersonApi(FactoryPersonService farmerService) {
		this.factoryPersonService = farmerService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get factory person by id",
			response = FactoryPerson.class)
	public Response find(@PathParam("id") Long id) {
		FactoryPerson factoryPerson = factoryPersonService.findById(id);
		if(factoryPerson==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(factoryPerson).build();
	}
		
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get all the factory persons",
			response = List.class)
	public List<FactoryPerson> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return factoryPersonService.findAll();
		else
			return factoryPersonService.findAll(limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the factory person",
			response = FactoryPerson.class)
	public Response save(String  jsonString) {
		try {
			FactoryPerson factoryPerson = factoryPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(factoryPerson).build();
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
