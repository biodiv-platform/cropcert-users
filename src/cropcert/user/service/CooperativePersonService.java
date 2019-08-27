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

import cropcert.user.dao.CooperativePersonDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.CooperativePerson;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class CooperativePersonService extends AbstractService<CooperativePerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@Inject
	public CooperativePersonService(CooperativePersonDao coPersonDao) {
		super(coPersonDao);
	}
	
	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.CO_PERSON);
		defaultPermissions.add(Permissions.CC_PERSON);
	}

	public CooperativePerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		CooperativePerson ccPerson = objectMapper.readValue(jsonString, CooperativePerson.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
