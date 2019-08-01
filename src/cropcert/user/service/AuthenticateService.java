package cropcert.user.service;

import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.profile.CommonProfile;

import com.google.inject.Inject;

import cropcert.user.util.SimpleUsernamePasswordAuthenticator;

public class AuthenticateService {

	@Inject
	private SimpleUsernamePasswordAuthenticator usernamePasswordAuthenticator;

	public CommonProfile authenticate(String username, String password) throws Exception {
		// Authenticate the user using the credentials provided
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		usernamePasswordAuthenticator.validate(credentials, null);
		return credentials.getUserProfile();
	}

	/*
	 * public Map<String, Object> buildTokenResponse(CommonProfile profile, long
	 * userId, boolean getNewRefreshToken) { return buildTokenResponse(profile,
	 * userService.findById(userId), getNewRefreshToken); }
	 */

	/**
	 * Builds a response for authentication. On success it returns a access token
	 * and optionally a refresh token
	 * 
	 * @param profile            dummy
	 * @param user               dummy
	 * @param getNewRefreshToken dummy
	 * @return dummy
	 */
	/*
	 * public Map<String, Object> buildTokenResponse(CommonProfile profile, User
	 * user, boolean getNewRefreshToken) { try {
	 * log.debug("Building token response for " + user);
	 * 
	 * String jwtToken = generateAccessToken(profile, user);
	 * 
	 * // tokenDao.openCurrentSessionWithTransaction(); // Return the access_token
	 * valid for 2 hrs and a new refreshToken on // the response Map<String, Object>
	 * result = new HashMap<String, Object>(); result.put("access_token", jwtToken);
	 * result.put("token_type", "bearer"); //
	 * result.put("pic",user.getProfilePic());
	 * 
	 * if (getNewRefreshToken) { log.debug("Generating new refresh token for " +
	 * user); // Removing all existing refreshTokens
	 * 
	 * List<Token> existingRefreshToken = tokenDao.findByUser(user); for (Token t :
	 * existingRefreshToken) { user.setToken(null); tokenDao.delete(t); }
	 * tokenDao.getCurrentSession().flush();
	 * 
	 * 
	 * // Generating a fresh refreshToken String refreshToken =
	 * generateRefreshToken();
	 * 
	 * Token rToken = new Token(refreshToken, TokenType.REFRESH, user);
	 * 
	 * tokenDao.save(rToken);
	 * 
	 * result.put("refresh_token", refreshToken);
	 * 
	 * user.setLastLoginDate(new Date()); userService.save(user); } return result; }
	 * catch (Exception e) { throw e; } finally { //
	 * tokenDao.closeCurrentSessionWithTransaction(); } }
	 */
}
