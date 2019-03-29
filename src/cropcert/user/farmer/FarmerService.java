package cropcert.user.farmer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.common.AbstractService;
import cropcert.user.util.PasswordEncoder;

public class FarmerService extends AbstractService<Farmer>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public FarmerService(FarmerDao farmerDao) {
		super(farmerDao);
	}

	public Farmer save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Farmer farmer = objectMapper.readValue(jsonString, Farmer.class);
		String password = farmer.getPassword();
		password = PasswordEncoder.encode(password);
		farmer.setPassword(password);
		return save(farmer);
	}
	
	public Farmer findByPropertyWithCondtion(String property, String value, String condition) {
		return dao.findByPropertyWithCondition(property, value, condition);
	}

	public List<Farmer> getByPropertyWithCondtion(String property, String value, String condition, int limit, int offset) {
		return dao.getByPropertyWithCondtion(property, value, condition, limit, offset);
	}
	
	public List<Farmer> getByPropertyWithCondtion(String property, int value, String condition, int limit, int offset) {
		return dao.getByPropertyWithCondtion(property, value, condition, limit, offset);
	}
}
