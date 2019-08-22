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

import cropcert.user.model.CollectionCenter;
import cropcert.user.model.Cooperative;
import cropcert.user.service.CooperativeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Path("co")
@Api("Co operative")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
public class CooperativeApi{

	private CooperativeService cooperativeService;
	
	@Inject
	public CooperativeApi(CooperativeService cooperativeService) {
		this.cooperativeService = cooperativeService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get co-operative by id",
			response = Cooperative.class)
	public Response find(@PathParam("id") Long id) {
		Cooperative cooperative = cooperativeService.findById(id);
		if(cooperative==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(cooperative).build();
	}

	@Path("code/{code}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get co-opearative by its code",
			response = CollectionCenter.class)
	public Response findByCode(@PathParam("code") Long code) {
		Cooperative cooperative = cooperativeService.findByPropertyWithCondtion("code", code, "=");
		if(cooperative==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok().entity(cooperative).build();
	}
	
	@Path("union")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get list of co-operative from given union",
			response = Cooperative.class,
			responseContainer = "List")
	public Response getByUnion(
			@DefaultValue("-1") @QueryParam("unionCode") Long unionCode) {
		List<Cooperative> cooperatives = cooperativeService.getByPropertyWithCondtion("unionCode", unionCode, "=", -1, -1);
		return Response.ok().entity(cooperatives).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get all the co-operative",
			response = Cooperative.class,
			responseContainer = "List")
	public Response findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		
		List<Cooperative> cooperatives;
		if(limit==-1 || offset ==-1)
			cooperatives = cooperativeService.findAll();
		else
			cooperatives = cooperativeService.findAll(limit, offset);
		return Response.ok().entity(cooperatives).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the co-operative",
			response = Cooperative.class)
	public Response save(String  jsonString) {
		try {
			Cooperative cooperative = cooperativeService.save(jsonString);
			return Response.status(Status.CREATED).entity(cooperative).build();
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
