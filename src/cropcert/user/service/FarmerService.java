package cropcert.user.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.FarmerDao;
import cropcert.user.model.Farmer;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class FarmerService extends AbstractService<Farmer>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@Inject
	public FarmerService(FarmerDao farmerDao) {
		super(farmerDao);
	}

	public Farmer save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Farmer farmer = objectMapper.readValue(jsonString, Farmer.class);
		String password = farmer.getPassword();
		//password = PasswordEncoder.encode(password);
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
