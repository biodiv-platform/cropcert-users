package cropcert.user.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.CoOperativePersonDao;
import cropcert.user.model.CoOperativePerson;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class CoOperativePersonService extends AbstractService<CoOperativePerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@Inject
	public CoOperativePersonService(CoOperativePersonDao coPersonDao) {
		super(coPersonDao);
	}

	public CoOperativePerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CoOperativePerson ccPerson = objectMapper.readValue(jsonString, CoOperativePerson.class);
		String password = ccPerson.getPassword();
		//password = PasswordEncoder.encode(password);
		password = passwordEncoder.encodePassword(password, null);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
