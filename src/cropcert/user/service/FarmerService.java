package cropcert.user.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.FarmerDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.Farmer;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class FarmerService extends AbstractService<Farmer>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.FARMER);
	}
	
	@Inject
	public FarmerService(FarmerDao farmerDao) {
		super(farmerDao);
	}

	public Farmer save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		Farmer farmer = objectMapper.readValue(jsonString, Farmer.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		farmer.setPassword(password);
		return save(farmer);
	}
	
	public Farmer findByPropertyWithCondtion(String property, String value, String condition) {
		return dao.findByPropertyWithCondition(property, value, condition);
	}

	public List<Farmer> getByPropertyWithCondtion(String property, Object value, String condition, int limit, int offset) {
		return dao.getByPropertyWithCondtion(property, value, condition, limit, offset);
	}
}
