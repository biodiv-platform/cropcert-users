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

import cropcert.user.model.CoOperative;
import cropcert.user.service.CoOperativeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Path("co")
@Api("Co operative")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
public class CoOperativeApi{

	private CoOperativeService coOperativeService;
	
	@Inject
	public CoOperativeApi(CoOperativeService coOperativeService) {
		this.coOperativeService = coOperativeService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get co-operative by id",
			response = CoOperative.class)
	public Response find(@PathParam("id") Long id) {
		CoOperative coOperative = coOperativeService.findById(id);
		if(coOperative==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(coOperative).build();
	}
	
	@Path("coCode")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get co-opearative by its code person by id",
			response = CoOperative.class)
	public Response getByCoCode(@DefaultValue("-1") @QueryParam("coCode") String coCodeString) {
		Integer coCode = Integer.parseInt(coCodeString);
		CoOperative coOperative = coOperativeService.findByPropertyWithCondtion("code", coCode, "=");
		return Response.ok().entity(coOperative).build();
	}
	
	@Path("union")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get list of co-operative from given union",
			response = CoOperative.class,
			responseContainer = "List")
	public Response getByUnion(
			@DefaultValue("-1") @QueryParam("unionCode") Long unionCode) {
		List<CoOperative> coOperatives = coOperativeService.getByPropertyWithCondtion("unionCode", unionCode, "=", -1, -1);
		return Response.ok().entity(coOperatives).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get all the co-operative",
			response = CoOperative.class,
			responseContainer = "List")
	public Response findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		
		List<CoOperative> coOperatives;
		if(limit==-1 || offset ==-1)
			coOperatives = coOperativeService.findAll();
		else
			coOperatives = coOperativeService.findAll(limit, offset);
		return Response.ok().entity(coOperatives).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the co-operative",
			response = CoOperative.class)
	public Response save(String  jsonString) {
		try {
			CoOperative coOperative = coOperativeService.save(jsonString);
			return Response.status(Status.CREATED).entity(coOperative).build();
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
