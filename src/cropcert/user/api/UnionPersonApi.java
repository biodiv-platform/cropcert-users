package cropcert.user.api;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;

import cropcert.user.model.UnionPerson;
import cropcert.user.service.UnionPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Path("unionPerson")
@Api("Union person")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
public class UnionPersonApi{

	private UnionPersonService unionPersonService;
	
	@Inject
	public UnionPersonApi(UnionPersonService unionService) {
		this.unionPersonService = unionService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get the Union person by id",
			response = UnionPerson.class)
	public Response find(@PathParam("id") Long id) {
		UnionPerson unionPerson = unionPersonService.findById(id);
		if(unionPerson==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(unionPerson).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the union person",
			response = UnionPerson.class)
	public Response save(String  jsonString) {
		try {
			UnionPerson unionPerson = unionPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(unionPerson).build();
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
			value = "Delete the Union person by id",
			response = UnionPerson.class)
	public Response delete(@PathParam("id") Long id) {
		UnionPerson unionPerson = unionPersonService.delete(id);
		return Response.status(Status.ACCEPTED).entity(unionPerson).build();
	}
}
