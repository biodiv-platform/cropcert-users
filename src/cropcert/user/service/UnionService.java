package cropcert.user.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.UnionDao;
import cropcert.user.model.Union;

public class UnionService extends AbstractService<Union>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public UnionService(UnionDao dao) {
		super(dao);
	}
	
	public Union save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Union union = objectMapper.readValue(jsonString, Union.class);
		return save(union);
	}
}
