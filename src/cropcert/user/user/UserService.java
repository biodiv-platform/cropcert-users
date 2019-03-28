package cropcert.user.user;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.common.AbstractService;
import cropcert.user.util.PasswordEncoder;


public class UserService extends AbstractService<User>{

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	public UserService(UserDao userDao) {
		super(userDao);
	}

	public User save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		User user = objectMappper.readValue(jsonString, User.class);
		String password = user.getPassword();
		password = PasswordEncoder.encode(password);
		user.setPassword(password);
		return save(user);
	}
	
	public User findByPropertyWithCondtion(String property, String value, String condition) {
		return dao.findByPropertyWithCondition(property, value, condition);
	}
}
