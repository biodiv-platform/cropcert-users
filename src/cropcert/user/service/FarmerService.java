package cropcert.user.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.ValidationException;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.dao.FarmerDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.CollectionCenter;
import cropcert.user.model.Cooperative;
import cropcert.user.model.Farmer;
import cropcert.user.model.Union;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class FarmerService extends AbstractService<Farmer>{

	@Inject ObjectMapper objectMapper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@Inject 
	private CollectionCenterService collectionCenterService;
	
	@Inject
	private CooperativeService cooperativeService;
	
	@Inject
	private UnionService unionService;

	private static Set<String> defaultPermissions;
	static {
		defaultPermissions = new HashSet<String>();
		defaultPermissions.add(Permissions.FARMER);
	}
	
	@Inject
	public FarmerService(FarmerDao farmerDao) {
		super(farmerDao);
	}

	public Farmer save(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException, ValidationException {
		Farmer farmer = objectMapper.readValue(jsonString, Farmer.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		farmer.setPassword(password);
		farmer.setPermissions(defaultPermissions);
		
		String membershipId = farmer.getMembershipId();
		Long ccCode = farmer.getCcCode();
		if(ccCode == null)
			throw new ValidationException("Collection center code is compulsory");
		CollectionCenter collectionCenter = collectionCenterService.findByPropertyWithCondtion("code", ccCode, "=");
		Cooperative cooperative = cooperativeService.findByPropertyWithCondtion("code", collectionCenter.getCoCode(), "=");
		
		if(membershipId == null) {
			membershipId = "";
			Long union = cooperative.getUnionCode();
			
			Long seqNumber = cooperative.getFarSeqNumber();
			Long numFarmer = cooperative.getNumFarmer();
			
			membershipId += union;
			membershipId += "-" + cooperative.getCode();
			membershipId += "-" + collectionCenter.getCode();
			membershipId += "-" + seqNumber;
			
			farmer.setFieldCoOrdinator(seqNumber);
			farmer.setMembershipId(membershipId);
			
			cooperative.setFarSeqNumber(seqNumber + 1);
			cooperative.setNumFarmer(numFarmer + 1);
			
			cooperativeService.update(cooperative);
		}
		farmer.setCcName(collectionCenter.getName());
		farmer.setCoName(cooperative.getName());
		Union unionObject = unionService.findById(cooperative.getUnionCode());
		farmer.setUnionName(unionObject.getName());
		
		return save(farmer);
	}
	
	public Farmer findByPropertyWithCondtion(String property, String value, String condition) {
		return dao.findByPropertyWithCondition(property, value, condition);
	}
}
