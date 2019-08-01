package cropcert.user.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import cropcert.user.service.AuthenticateService;
import cropcert.user.util.AuthUtility;
import io.swagger.annotations.Api;

@Path("auth")
@Api("Authenticate")
public class AuthenticateApi {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateApi.class);
	
	@Inject
	private AuthenticateService authenticateService;
	
	@GET
	@Path("me")
	@Produces(MediaType.APPLICATION_JSON)
	public CommonProfile getUser(@Context HttpServletRequest request) {
		return AuthUtility.getCurrentUser(request);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticate(
			@FormParam("userName") String userName, 
			@FormParam("password") String password
			) {
		try {
			CommonProfile profile = authenticateService.authenticate(userName, password);
			//Map<String, Object> result = authenticateService.buildTokenResponse(profile, Long.parseLong(profile.getId()), true);
			//return Response.ok().entity(result).build();
			System.out.println(profile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
}
