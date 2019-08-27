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

import cropcert.user.dao.CollectionCenterPersonDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.CollectionCenterPerson;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class CollectionCenterPersonService extends AbstractService<CollectionCenterPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.CC_PERSON);
	}
	
	@Inject
	public CollectionCenterPersonService(CollectionCenterPersonDao ccPersonDao) {
		super(ccPersonDao);
	}

	public CollectionCenterPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		CollectionCenterPerson ccPerson = objectMapper.readValue(jsonString, CollectionCenterPerson.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		ccPerson.setPassword(password);
		ccPerson.setPermissions(defaultPermissions);
		return save(ccPerson);
	}

}
