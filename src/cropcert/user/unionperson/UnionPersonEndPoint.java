package cropcert.user.unionperson;

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


@Path("unionPerson")
public class UnionPersonEndPoint{

	private UnionPersonService unionPersonService;
	
	@Inject
	public UnionPersonEndPoint(UnionPersonService unionService) {
		this.unionPersonService = unionService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		UnionPerson unionPerson = unionPersonService.findById(id);
		if(unionPerson==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(unionPerson).build();
	}
		
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UnionPerson> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return unionPersonService.findAll();
		else
			return unionPersonService.findAll(limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
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
	public Response delete(@PathParam("id") Long id) {
		UnionPerson unionPerson = unionPersonService.delete(id);
		return Response.status(Status.ACCEPTED).entity(unionPerson).build();
	}

}
