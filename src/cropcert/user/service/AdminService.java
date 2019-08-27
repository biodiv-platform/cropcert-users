package cropcert.user.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.AdminDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.Admin;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class AdminService extends AbstractService<Admin>{

	@Inject 
	private ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.ADMIN);
	}
	
	@Inject
	public AdminService(AdminDao adminDao) {
		super(adminDao);
	}

	public Admin save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		Admin admin = objectMapper.readValue(jsonString, Admin.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		admin.setPassword(password);
		admin.setPermissions(defaultPermissions);
		return save(admin);
	}

}
