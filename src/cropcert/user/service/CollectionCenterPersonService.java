package cropcert.user.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.CollectionCenterPersonDao;
import cropcert.user.model.CollectionCenterPerson;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class CollectionCenterPersonService extends AbstractService<CollectionCenterPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@Inject
	public CollectionCenterPersonService(CollectionCenterPersonDao ccPersonDao) {
		super(ccPersonDao);
	}

	public CollectionCenterPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CollectionCenterPerson ccPerson = objectMapper.readValue(jsonString, CollectionCenterPerson.class);
		String password = ccPerson.getPassword();
		//password = PasswordEncoder.encode(password);
		password = passwordEncoder.encodePassword(password, null);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
