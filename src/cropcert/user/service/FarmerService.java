package cropcert.user.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.opencsv.CSVReader;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import cropcert.user.dao.FarmerDao;
import cropcert.user.filter.Permissions;
import cropcert.user.model.CollectionCenter;
import cropcert.user.model.Cooperative;
import cropcert.user.model.Farmer;
import cropcert.user.model.Union;
import cropcert.user.model.request.FarmerFileMetaData;
import cropcert.user.util.MessageDigestPasswordEncoder;

public class FarmerService extends AbstractService<Farmer> {

	@Inject
	ObjectMapper objectMapper;

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

	public Farmer save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException, ValidationException {
		Farmer farmer = objectMapper.readValue(jsonString, Farmer.class);
		JSONObject jsonObject = new JSONObject(jsonString);
		String password = jsonObject.getString("password");
		password = passwordEncoder.encodePassword(password, null);
		farmer.setPassword(password);
		farmer.setPermissions(defaultPermissions);

		String membershipId = farmer.getMembershipId();
		Long ccCode = farmer.getCcCode();
		if (ccCode == null)
			throw new ValidationException("Collection center code is compulsory");
		CollectionCenter collectionCenter = collectionCenterService.findByPropertyWithCondition("code", ccCode, "=");
		Cooperative cooperative = cooperativeService.findByPropertyWithCondition("code", collectionCenter.getCoCode(),
				"=");

		if (membershipId == null) {
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
	
	@Override
	public Farmer save(Farmer farmer) {
		String password = farmer.getPassword();
		password = passwordEncoder.encodePassword(password, null);
		farmer.setPassword(password);
		farmer.setPermissions(defaultPermissions);
		return super.save(farmer);
	}
	
	public List<Farmer> getFarmerForMultipleCollectionCenter(String ccCodes, String firstName, Integer limit,
			Integer offset) {
		List<Long> ccCodesLong = new ArrayList<Long>();

		String[] ccCodesString = ccCodes.split(",");
		for (String ccCodeString : ccCodesString) {
			Long ccCode = Long.parseLong(ccCodeString);
			ccCodesLong.add(ccCode);
		}

		return ((FarmerDao) dao).getFarmerForMultipleCollectionCenter(ccCodesLong, firstName, limit, offset);
	}

	public Map<String, Object> bulkFarmerSave(HttpServletRequest request, FormDataMultiPart multiPart) throws IOException {

		FormDataBodyPart formdata = multiPart.getField("metadata");
		if (formdata == null) {
			throw new WebApplicationException(
					Response.status(Response.Status.BAD_REQUEST).entity("Metadata file not present").build());
		}
		InputStream metaDataInputStream = formdata.getValueAs(InputStream.class);
		InputStreamReader inputStreamReader = new InputStreamReader(metaDataInputStream, StandardCharsets.UTF_8);
		
		FarmerFileMetaData fileMetaData = objectMapper.readValue(inputStreamReader, FarmerFileMetaData.class);
		fileMetaData.setCollectionCenterService(collectionCenterService);
		fileMetaData.setCooperativeService(cooperativeService);
		fileMetaData.setUnionService(unionService);

		
		if(fileMetaData.getFileType().equalsIgnoreCase("csv")) {
			
			formdata = multiPart.getField("csv");
			InputStream csvDataInputStream = formdata.getValueAs(InputStream.class);
			inputStreamReader = new InputStreamReader(csvDataInputStream, StandardCharsets.UTF_8);
			CSVReader reader = new CSVReader(inputStreamReader);
			
			Map<String, Object> result = ValidateSheet(reader, fileMetaData);
			if (result.size() > 0)
				return result;
			
			csvDataInputStream = formdata.getValueAs(InputStream.class);
			inputStreamReader = new InputStreamReader(csvDataInputStream, StandardCharsets.UTF_8);
			reader = new CSVReader(inputStreamReader);
			Iterator<String[]> it = reader.iterator();

			@SuppressWarnings("unused")
			String[] headers = it.next();
			
			while (it.hasNext()) {
				String[] data = it.next();
				try {
				
					Farmer farmer = fileMetaData.readOneRow(data, false);
					farmer = save(farmer);
					result.put("Success", farmer);
				} catch(Exception e) {
					reader.close();
					throw new IOException("Error creating the farmer : " + data);
				}
			}
			reader.close();
			return result;
		}
		throw new IOException("CSV File as input is compulsory");
	}

	private Map<String, Object> ValidateSheet(CSVReader reader, FarmerFileMetaData fileMetaData) {
		
		Map<String, Object> validationResult = new HashMap<String, Object>();
		
		Iterator<String[]> it = reader.iterator();

		String[] headers = it.next();
		if(!fileMetaData.validateIndices(headers)) {
			validationResult.put("Failed", "Compulsory column indices in the file are required for metadata");
			return validationResult;
		}

		int index = 1;
		while (it.hasNext()) {
			String[] data = it.next();
			try {
				fileMetaData.readOneRow(data, true);
			} catch (IOException e) {
				validationResult.put("Farmer index :  " + index , e.getMessage());
			}
			index ++;
		}
		
		return validationResult;
	}
}
