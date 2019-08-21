package cropcert.user.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.CooperativePersonDao;
import cropcert.user.model.CooperativePerson;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class CooperativePersonService extends AbstractService<CooperativePerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@Inject
	public CooperativePersonService(CooperativePersonDao coPersonDao) {
		super(coPersonDao);
	}

	public CooperativePerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CooperativePerson ccPerson = objectMapper.readValue(jsonString, CooperativePerson.class);
		String password = ccPerson.getPassword();
		//password = PasswordEncoder.encode(password);
		password = passwordEncoder.encodePassword(password, null);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
