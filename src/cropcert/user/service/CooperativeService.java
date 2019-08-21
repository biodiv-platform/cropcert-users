package cropcert.user.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.CooperativeDao;
import cropcert.user.model.Cooperative;

public class CooperativeService extends AbstractService<Cooperative>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public CooperativeService(CooperativeDao dao) {
		super(dao);
	}
	
	public Cooperative save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Cooperative coOperative = objectMapper.readValue(jsonString, Cooperative.class);
		return save(coOperative);
	}

}
