package cropcert.user.factoryperson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.common.AbstractService;
import cropcert.user.util.PasswordEncoder;

public class FactoryPersonService extends AbstractService<FactoryPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public FactoryPersonService(FactoryPersonDao factoryPersonDao) {
		super(factoryPersonDao);
	}

	public FactoryPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		FactoryPerson ccPerson = objectMapper.readValue(jsonString, FactoryPerson.class);
		String password = ccPerson.getPassword();
		password = PasswordEncoder.encode(password);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
