package cropcert.user.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.FactoryPersonDao;
import cropcert.user.model.FactoryPerson;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class FactoryPersonService extends AbstractService<FactoryPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@Inject
	public FactoryPersonService(FactoryPersonDao factoryPersonDao) {
		super(factoryPersonDao);
	}

	public FactoryPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		FactoryPerson ccPerson = objectMapper.readValue(jsonString, FactoryPerson.class);
		String password = ccPerson.getPassword();
		//password = PasswordEncoder.encode(password);
		password = passwordEncoder.encodePassword(password, null);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
