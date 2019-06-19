package cropcert.user.coperson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.common.AbstractService;
import cropcert.user.util.PasswordEncoder;

public class COPersonService extends AbstractService<COPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public COPersonService(COPersonDao coPersonDao) {
		super(coPersonDao);
	}

	public COPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		COPerson ccPerson = objectMapper.readValue(jsonString, COPerson.class);
		String password = ccPerson.getPassword();
		password = PasswordEncoder.encode(password);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
