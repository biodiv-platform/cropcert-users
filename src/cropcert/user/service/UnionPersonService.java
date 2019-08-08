package cropcert.user.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.UnionPersonDao;
import cropcert.user.model.UnionPerson;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class UnionPersonService extends AbstractService<UnionPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@Inject
	public UnionPersonService(UnionPersonDao coPersonDao) {
		super(coPersonDao);
	}

	public UnionPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		UnionPerson ccPerson = objectMapper.readValue(jsonString, UnionPerson.class);
		String password = ccPerson.getPassword();
		//password = PasswordEncoder.encode(password);
		password = passwordEncoder.encodePassword(password, null);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
