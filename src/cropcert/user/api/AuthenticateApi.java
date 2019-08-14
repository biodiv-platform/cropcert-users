package cropcert.user.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jax.rs.annotations.Pac4JSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import cropcert.user.filter.JWTTokenValidationFilter;
import cropcert.user.service.AuthenticateService;
import cropcert.user.util.AuthUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("auth")
@Api(value = "Authenticate")
public class AuthenticateApi {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateApi.class);
	
	@Inject
	private AuthenticateService authenticateService;

	@Inject
	private JWTTokenValidationFilter jwtTokenValidationFilter;
	
	@GET
	@Path("me")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
	                      required = true, dataType = "string", paramType = "header") })
	@ApiOperation(
			value = "Get the current user",
			response = CommonProfile.class)
	public CommonProfile getUser(
			@Context HttpServletRequest request,
			@Context HttpServletResponse response
			) {
		return AuthUtility.getCurrentUser(request, response);
	}
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(
			value = "Authenticate the user by userName and password",
			response = Map.class)
	public Response authenticate(
			@FormParam("userName") String userName, 
			@FormParam("password") String password
			) {
		try {
			CommonProfile profile = authenticateService.authenticate(userName, password);
			Map<String, Object> result = authenticateService.buildTokenResponse(profile, Long.parseLong(profile.getId()), true);
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("newTokens")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Pac4JSecurity(clients = "headerClient", authorizers = "isAuthenticated")
	@ApiResponses(value = {
			@ApiResponse(code = 406, message = "Invalid refresh token")
	})
	@ApiOperation(
			value = "Get new set of refresh token and access token",
			response = Map.class)
	public Response getNewSetOfTokens(
			@Context HttpServletRequest request,
			@Context HttpServletResponse response, 
			@QueryParam("refreshToken") String refreshToken) {
		
		// check for the valid refresh token
		CommonProfile profile = jwtTokenValidationFilter.isTokenValid(refreshToken);
		if( profile == null) {
			System.out.println("Invalid refresh token");
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalid refresh token").build();
		}
		try {
			Map<String, Object> result = authenticateService.buildTokenResponse(profile, Long.parseLong(profile.getId()), true);
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}
}
