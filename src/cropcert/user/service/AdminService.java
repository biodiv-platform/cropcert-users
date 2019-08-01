package cropcert.user.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.AdminDao;
import cropcert.user.model.Admin;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class AdminService extends AbstractService<Admin>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@Inject
	public AdminService(AdminDao adminDao) {
		super(adminDao);
	}

	public Admin save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Admin admin = objectMapper.readValue(jsonString, Admin.class);
		String password = admin.getPassword();
		password = passwordEncoder.encodePassword(password, null);
		admin.setPassword(password);
		return save(admin);
	}

}
