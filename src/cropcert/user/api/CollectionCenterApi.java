package cropcert.user.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import cropcert.user.service.CollectionCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("cc")
@Api("Collection  center")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
public class CollectionCenterApi{

	private CollectionCenterService collectionCenterService;
	
	@Inject
	public CollectionCenterApi(CollectionCenterService collectionCenterService) {
		this.collectionCenterService = collectionCenterService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get cc by id",
			response = CollectionCenter.class)
	public Response find(@PathParam("id") Long id) {
		CollectionCenter collectionCenter = collectionCenterService.findById(id);
		if(collectionCenter==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(collectionCenter).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get all the collection centers",
			response = CollectionCenter.class,
			responseContainer = "List")
	public Response findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<CollectionCenter> collectionCenters;
		if(limit==-1 || offset ==-1)
			collectionCenters = collectionCenterService.findAll();
		else
			collectionCenters = collectionCenterService.findAll(limit, offset);
		return Response.ok().entity(collectionCenters).build();
	}

	@Path("coOperativeId/{coOperativeId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get list of cc by co-operative code",
			response = CollectionCenter.class,
			responseContainer = "List")
	public Response findAll(
			@PathParam("coOperativeId") Long coOperativeId) {
		List<CollectionCenter> collectionCenters = collectionCenterService.getByPropertyWithCondtion("coOperativeId", coOperativeId, "=", -1, -1);
		return Response.ok().entity(collectionCenters).build();
	}
	
	@Path("origin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get map of origins by cc codes",
			response = Map.class,
			responseContainer = "Map")
	public Response getOriginNames(@DefaultValue("") @QueryParam("ccCodes") String ccCodes) {
		Map<String, Object> originMap = collectionCenterService.getOriginNames(ccCodes);
		return Response.ok().entity(originMap).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the cc",
			response = CollectionCenter.class)
	public Response save(String  jsonString) {
		try {
			CollectionCenter collectionCenter = collectionCenterService.save(jsonString);
			return Response.status(Status.CREATED).entity(collectionCenter).build();
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
