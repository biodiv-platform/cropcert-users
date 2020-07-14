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

import cropcert.user.dao.ICSManagerDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.ICSManager;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class ICSManagerService extends AbstractService<ICSManager>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.ICS_MANAGER);
	}
	
	@Inject
	public ICSManagerService(ICSManagerDao icsManagerDao) {
		super(icsManagerDao);
	}

	public ICSManager save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		ICSManager icsManager = objectMapper.readValue(jsonString, ICSManager.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		icsManager.setPassword(password);
		icsManager.setPermissions(defaultPermissions);
		return save(icsManager);
	}

}
