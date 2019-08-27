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

import cropcert.user.dao.UnionPersonDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.UnionPerson;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class UnionPersonService extends AbstractService<UnionPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.UNION);
		defaultPermissions.add(Permissions.FACTORY);
	}
	
	@Inject
	public UnionPersonService(UnionPersonDao coPersonDao) {
		super(coPersonDao);
	}

	public UnionPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		UnionPerson ccPerson = objectMapper.readValue(jsonString, UnionPerson.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
