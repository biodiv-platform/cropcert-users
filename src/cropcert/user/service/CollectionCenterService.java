package cropcert.user.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.api.CooperativeApi;
import cropcert.user.dao.CollectionCenterDao;
import cropcert.user.model.Cooperative;
import cropcert.user.model.CollectionCenter;

public class CollectionCenterService extends AbstractService<CollectionCenter>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private CooperativeApi cooperativeService;
	
	@Inject
	public CollectionCenterService(CollectionCenterDao dao) {
		super(dao);
	}
	
	public CollectionCenter save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		CollectionCenter collectionCenter = objectMapper.readValue(jsonString, CollectionCenter.class);
		return save(collectionCenter);
	}

	public Map<String, Object> getOriginNames(String ccCodesString) {
		Long coCode = -1L;
		List<String> ccNames = new ArrayList<String>();
		
		for(String value : ccCodesString.split(",")) {
			Long ccCode = Long.parseLong(value);
			CollectionCenter collectionCenter = findByPropertyWithCondtion("code", ccCode, "=");
			coCode = collectionCenter.getCoCode();
			ccNames.add(collectionCenter.getName());
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(coCode != null && coCode != -1) {
			Response r = cooperativeService.findByCode(coCode);
			if(r.getEntity() != null) {
				Cooperative cooperative = (Cooperative) r.getEntity();
				result.put("cooperativeName", cooperative.getName());
			}
		}
		result.put("ccNames", ccNames);
		
		return result;
	}

}
