package cropcert.user.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.pac4j.core.profile.CommonProfile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.user.api.CollectionCenterApi;
import cropcert.user.api.CooperativeApi;
import cropcert.user.dao.UserDao;
import cropcert.user.model.CollectionCenter;
import cropcert.user.model.CollectionCenterPerson;
import cropcert.user.model.Cooperative;
import cropcert.user.model.CooperativePerson;
import cropcert.user.model.UnionPerson;
import cropcert.user.model.User;
import cropcert.user.util.AuthUtility;
import cropcert.user.util.MessageDigestPasswordEncoder;


public class UserService extends AbstractService<User>{

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	private MessageDigestPasswordEncoder passwordEncoder;

	@Inject
	private CooperativeApi cooperativeApi;
	
	@Inject
	private CollectionCenterApi collectionCenterApi;
	
	@Inject
	public UserService(UserDao userDao) {
		super(userDao);
	}
	
	public Map<String, Object> getMyData(HttpServletRequest request) {
		CommonProfile profile = AuthUtility.getCurrentUser(request);
		User user = findById(Long.parseLong(profile.getId()));
		
		Map<String, Object> myData = new HashMap<String, Object>();
		/*
		 * The normal user data
		 */
		myData.put("id", user.getId());
		myData.put("userName", user.getUserName());
		myData.put("email", user.getEmail());
		myData.put("firstName", user.getFirstName());
		myData.put("lastName", user.getLastName());
		myData.put("gender", user.getGender());
		myData.put("cellNumber", user.getCellNumber());
		myData.put("dateOfBirth", user.getDateOfBirth());
		myData.put("role", user.getRole());
		
		/*
		 * Insert data specific to user
		 */
		myData.put("ccCode", -1);
		myData.put("coCode", -1);
		myData.put("unionCode", -1);
		
		if (user instanceof UnionPerson) {
			myData.put("unionCode", ((UnionPerson) user).getUnionCode());
		} 
		else if (user instanceof CooperativePerson) {
			CooperativePerson coPerson = (CooperativePerson) user;
			
			int coCode = coPerson.getCoCode();
			myData.put("coCode", coPerson.getCoCode());
			
			Response coResponse = cooperativeApi.findByCode(request, (long) coCode);
			if(coResponse.getEntity() != null) {
				Cooperative cooperative = (Cooperative) coResponse.getEntity();
				myData.put("unionCode", cooperative.getUnionCode());
			}
		} 
		else if (user instanceof CollectionCenterPerson) {
			CollectionCenterPerson ccPerson = (CollectionCenterPerson) user;
			
			int ccCode = ccPerson.getCcCode();
			myData.put("ccCode", ccCode);
			
			Response ccResponse = collectionCenterApi.findByCode(request, (long) ccCode);
			if(ccResponse.getEntity() != null) {
				CollectionCenter collectionCenter = (CollectionCenter) ccResponse.getEntity();
				Long coCode = collectionCenter.getCoCode();
				myData.put("coCode", coCode);
				
				Response coResponse = cooperativeApi.findByCode(request, (long) coCode);
				if(coResponse.getEntity() != null) {
					Cooperative cooperative = (Cooperative) coResponse.getEntity();
					myData.put("unionCode", cooperative.getUnionCode());
				}
			}
		}
		return myData;
	}

	public User save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		User user = objectMappper.readValue(jsonString, User.class);
		String password = user.getPassword();
		//password = PasswordEncoder.encode(password);
		password = passwordEncoder.encodePassword(password, null);
		user.setPassword(password);
		return save(user);
	}

	public User getByEmail(String email) {
		return findByPropertyWithCondtion("email", email, "=");
	}
	
	public User getByUserName(String userName) {
		return findByPropertyWithCondtion("userName", userName, "=");
	}
	
	public User findByPropertyWithCondtion(String property, String value, String condition) {
		return dao.findByPropertyWithCondition(property, value, condition);
	}

}
