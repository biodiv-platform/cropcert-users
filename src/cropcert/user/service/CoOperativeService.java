package cropcert.user.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.CoOperativeDao;
import cropcert.user.model.CoOperative;

public class CoOperativeService extends AbstractService<CoOperative>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public CoOperativeService(CoOperativeDao dao) {
		super(dao);
	}
	
	public CoOperative save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CoOperative coOperative = objectMapper.readValue(jsonString, CoOperative.class);
		return save(coOperative);
	}

}
