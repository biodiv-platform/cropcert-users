package cropcert.user.unionperson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.common.AbstractService;
import cropcert.user.util.PasswordEncoder;

public class UnionPersonService extends AbstractService<UnionPerson>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	public UnionPersonService(UnionPersonDao coPersonDao) {
		super(coPersonDao);
	}

	public UnionPerson save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		UnionPerson ccPerson = objectMapper.readValue(jsonString, UnionPerson.class);
		String password = ccPerson.getPassword();
		password = PasswordEncoder.encode(password);
		ccPerson.setPassword(password);
		return save(ccPerson);
	}

}
