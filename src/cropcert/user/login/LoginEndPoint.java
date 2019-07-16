package cropcert.user.login;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;

import cropcert.user.user.User;
import cropcert.user.user.UserService;
import cropcert.user.util.UserDetailUtil;

@Path("me")
public class LoginEndPoint {

	@Inject private UserService userService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@Context HttpServletRequest request) {
		return UserDetailUtil.getUserDetails(request, userService);
	}
}
