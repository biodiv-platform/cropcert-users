package cropcert.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.definition.CommonProfileDefinition;
import org.pac4j.core.profile.jwt.JwtClaims;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;

import com.google.inject.Inject;

import cropcert.user.MyApplication;
import cropcert.user.api.CollectionCenterApi;
import cropcert.user.api.CooperativeApi;
import cropcert.user.model.CollectionCenter;
import cropcert.user.model.CollectionCenterPerson;
import cropcert.user.model.Cooperative;
import cropcert.user.model.CooperativePerson;
import cropcert.user.model.UnionPerson;
import cropcert.user.model.User;
import cropcert.user.util.AuthUtility;
import cropcert.user.util.SimpleUsernamePasswordAuthenticator;

public class AuthenticateService {

	@Inject
	private UserService userService;
	
	@Inject
	private CooperativeApi cooperativeApi;
	
	@Inject
	private CollectionCenterApi collectionCenterApi;

	@Inject
	private SimpleUsernamePasswordAuthenticator usernamePasswordAuthenticator;

	public CommonProfile authenticate(String username, String password) throws Exception {
		// Authenticate the user using the credentials provided
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		usernamePasswordAuthenticator.validate(credentials, null);
		return credentials.getUserProfile();
	}

	public Map<String, Object> buildTokenResponse(CommonProfile profile, long userId, boolean getNewRefreshToken) {
		return buildTokenResponse(profile, userService.findById(userId), getNewRefreshToken);
	}

	/**
	 * Builds a response for authentication. On success it returns a access token
	 * and optionally a refresh token
	 * 
	 * @param profile            dummy
	 * @param user               dummy
	 * @param getNewRefreshToken dummy
	 * @return dummy
	 */

	public Map<String, Object> buildTokenResponse(CommonProfile profile, User user, boolean getNewRefreshToken) {
		try {
			String jwtToken = generateAccessToken(profile, user);

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("access_token", jwtToken);
			result.put("token_type", "bearer");
			result.put("timeout", AuthUtility.getAccessTokenExpiryDate());

			if (getNewRefreshToken) {
				String refreshToken = generateRefreshToken(profile, user);
				result.put("refresh_token", refreshToken);
			}
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Generates access token in JWT format encrypted with JWT_SALT as secret for
	 * the profile.
	 * 
	 * @param profile dummy
	 * @return TODO : use bcrypt encryption for token
	 */
	private String generateAccessToken(CommonProfile profile, User user) {

		JwtGenerator<CommonProfile> generator = new JwtGenerator<CommonProfile>(
				new SecretSignatureConfiguration(MyApplication.JWT_SALT));
		
		Set<String> roles = new HashSet<String>();
		roles.add(user.getRole());

		Map<String, Object> jwtClaims = new HashMap<String, Object>();
		jwtClaims.put("id", profile.getId());
		jwtClaims.put(JwtClaims.SUBJECT, profile.getId() + "");
		jwtClaims.put(Pac4jConstants.USERNAME, profile.getUsername());
		jwtClaims.put(CommonProfileDefinition.EMAIL, profile.getEmail());
		jwtClaims.put(JwtClaims.EXPIRATION_TIME, AuthUtility.getAccessTokenExpiryDate());
		jwtClaims.put(JwtClaims.ISSUED_AT, new Date());
		jwtClaims.put("roles", roles);

		String jwtToken = generator.generate(jwtClaims);
		return jwtToken;
	}

	/**
	 * Generates a refresh token which is a plain string used to identify user.
	 * 
	 * @return dummy
	 */
	private String generateRefreshToken(CommonProfile profile, User user) {
		JwtGenerator<CommonProfile> generator = new JwtGenerator<CommonProfile>(
				new SecretSignatureConfiguration(MyApplication.JWT_SALT));

		Set<String> roles = new HashSet<String>();
		roles.add(user.getRole());

		Map<String, Object> jwtClaims = new HashMap<String, Object>();
		jwtClaims.put("id", profile.getId());
		jwtClaims.put(JwtClaims.SUBJECT, profile.getId() + "");
		jwtClaims.put(Pac4jConstants.USERNAME, profile.getUsername());
		jwtClaims.put(CommonProfileDefinition.EMAIL, profile.getEmail());
		jwtClaims.put(JwtClaims.EXPIRATION_TIME, AuthUtility.getRefreshTokenExpiryDate());
		jwtClaims.put(JwtClaims.ISSUED_AT, new Date());
		jwtClaims.put("roles", roles);

		generator.setExpirationTime(AuthUtility.getRefreshTokenExpiryDate());
		
		String jwtToken = generator.generate(jwtClaims);
		return jwtToken;

	}

	public Map<String, Object> getMyData(HttpServletRequest request) {
		CommonProfile profile = AuthUtility.getCurrentUser(request);
		User user = userService.findById(Long.parseLong(profile.getId()));
		
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
			
			Response coResponse = cooperativeApi.findByCode((long) coCode);
			if(coResponse.getEntity() != null) {
				Cooperative cooperative = (Cooperative) coResponse.getEntity();
				myData.put("unionCode", cooperative.getUnionCode());
			}
		} 
		else if (user instanceof CollectionCenterPerson) {
			CollectionCenterPerson ccPerson = (CollectionCenterPerson) user;
			
			int ccCode = ccPerson.getCcCode();
			myData.put("ccCode", ccCode);
			
			Response ccResponse = collectionCenterApi.findByCode((long) ccCode);
			if(ccResponse.getEntity() != null) {
				CollectionCenter collectionCenter = (CollectionCenter) ccResponse.getEntity();
				Long coCode = collectionCenter.getCooperativeCode();
				myData.put("coCode", coCode);
				
				Response coResponse = cooperativeApi.findByCode((long) coCode);
				if(coResponse.getEntity() != null) {
					Cooperative cooperative = (Cooperative) coResponse.getEntity();
					myData.put("unionCode", cooperative.getUnionCode());
				}
			}
		}
		return myData;
	}
}
