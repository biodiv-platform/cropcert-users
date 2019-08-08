package cropcert.user.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;

import cropcert.user.model.FactoryPerson;
import cropcert.user.service.FactoryPersonService;
import io.swagger.annotations.Api;


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
	public Response find(@PathParam("id") Long id) {
		FactoryPerson ccPerson = factoryPersonService.findById(id);
		if(ccPerson==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(ccPerson).build();
	}
		
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<FactoryPerson> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return factoryPersonService.findAll();
		else
			return factoryPersonService.findAll(limit, offset);
	}
}
