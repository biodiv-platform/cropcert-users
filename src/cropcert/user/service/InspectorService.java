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

import cropcert.user.dao.InspectorDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.Inspector;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class InspectorService extends AbstractService<Inspector>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.INSPECTOR);
	}
	
	@Inject
	public InspectorService(InspectorDao inspectorDao) {
		super(inspectorDao);
	}

	public Inspector save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		Inspector inspector = objectMapper.readValue(jsonString, Inspector.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		inspector.setPassword(password);
		inspector.setPermissions(defaultPermissions);
		return save(inspector);
	}

}
