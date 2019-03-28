package cropcert.user.ccperson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.common.AbstractService;
import cropcert.user.util.PasswordEncoder;

public class CCPersonService extends AbstractService<CCPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public CCPersonService(CCPersonDao ccPersonDao) {
		super(ccPersonDao);
	}

	public CCPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CCPerson ccPerson = objectMapper.readValue(jsonString, CCPerson.class);
		String password = ccPerson.getPassword();
		password = PasswordEncoder.encode(password);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
