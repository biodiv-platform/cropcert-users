package cropcert.user.login;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jasig.cas.client.validation.Assertion;

import com.google.inject.Inject;

import cropcert.user.user.User;
import cropcert.user.user.UserService;

@Path("me")
public class LoginEndPoint {

	@Inject private UserService userService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@Context HttpServletRequest request) {
		
		Assertion assertion = (Assertion) request.getSession().getAttribute("_const_cas_assertion_");
		String email        = assertion.getPrincipal().getName();
		return userService.findByPropertyWithCondtion("email", email, "="); 
	}
	
	@Path("role")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getRole(@Context HttpServletRequest request) {
		System.out.println(request.getAttributeNames());
		System.out.println(request.getSession().getAttributeNames());
		
		Enumeration<String> names = request.getSession().getAttributeNames();
		while(names.hasMoreElements())
			System.out.println(names.nextElement());
		names = request.getAttributeNames();
		while(names.hasMoreElements())
			System.out.println(names.nextElement());
		Assertion assertion = (Assertion) request.getSession().getAttribute("_const_cas_assertion_");
		Map<String, Object> m = assertion.getAttributes();
		
		for(String key : m.keySet()) {
			System.out.println(key + " : " + m.get(key));
		}
		String id = assertion.getPrincipal().getName();
		return id;
	}
}
