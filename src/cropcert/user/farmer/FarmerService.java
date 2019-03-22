package cropcert.user.farmer;

import java.io.IOException;

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

}
