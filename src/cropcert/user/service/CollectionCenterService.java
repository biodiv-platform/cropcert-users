package cropcert.user.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.CollectionCenterDao;
import cropcert.user.model.CoOperative;
import cropcert.user.model.CollectionCenter;

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
			CollectionCenter collectionCenter = findByPropertyWithCondtion("code", ccCode, "=");
			coOperativeId = collectionCenter.getCoOperativeCode();
			ccNames.add(collectionCenter.getName());
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(coOperativeId != null && coOperativeId != -1) {
			CoOperative coOperative = coOperativeService.findById(coOperativeId);
			result.put("coOperativeName", coOperative.getName());
		}
		result.put("ccNames", ccNames);
		
		return result;
	}

}
