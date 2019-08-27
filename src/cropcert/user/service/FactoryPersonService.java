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

import cropcert.user.dao.FactoryPersonDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.FactoryPerson;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class FactoryPersonService extends AbstractService<FactoryPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.FACTORY);
	}
	
	@Inject
	public FactoryPersonService(FactoryPersonDao factoryPersonDao) {
		super(factoryPersonDao);
	}

	public FactoryPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		FactoryPerson factoryPerson = objectMapper.readValue(jsonString, FactoryPerson.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		factoryPerson.setPassword(password);
		factoryPerson.setPermissions(defaultPermissions);
		return save(factoryPerson);
	}

}
