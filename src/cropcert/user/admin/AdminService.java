package cropcert.user.admin;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.common.AbstractService;
import cropcert.user.util.PasswordEncoder;

public class AdminService extends AbstractService<Admin>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public AdminService(AdminDao adminDao) {
		super(adminDao);
	}

	public Admin save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Admin admin = objectMapper.readValue(jsonString, Admin.class);
		String password = admin.getPassword();
		password = PasswordEncoder.encode(password);
		admin.setPassword(password);
		return save(admin);
	}

}
