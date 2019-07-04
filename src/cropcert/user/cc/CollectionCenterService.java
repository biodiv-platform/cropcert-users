package cropcert.user.cc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.co.CoOperative;
import cropcert.user.co.CoOperativeService;
import cropcert.user.common.AbstractService;

public class CollectionCenterService extends AbstractService<CollectionCenter>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private CoOperativeService coOperativeService;
	
	@Inject
	public CollectionCenterService(CollectionCenterDao dao) {
		super(dao);
	}
	
	public CollectionCenter save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CollectionCenter collectionCenter = objectMapper.readValue(jsonString, CollectionCenter.class);
		return save(collectionCenter);
	}

	public Map<String, Object> getOriginNames(String ccCodesString) {
		Long coOperativeId = -1L;
		List<String> ccNames = new ArrayList<String>();
		
		for(String value : ccCodesString.split(",")) {
			Long ccCode = Long.parseLong(value);
			CollectionCenter collectionCenter = findById(ccCode);
			coOperativeId = collectionCenter.getCoOperativeId();
			ccNames.add(collectionCenter.getCcName());
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(coOperativeId != -1) {
			CoOperative coOperative = coOperativeService.findById(coOperativeId);
			result.put("coOperativeName", coOperative.getCoName());
		}
		result.put("ccNames", ccNames);
		
		return result;
	}

}
