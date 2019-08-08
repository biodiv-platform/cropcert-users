package cropcert.user.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.bouncycastle.util.encoders.Hex;

import com.google.inject.Inject;

public class MessageDigestPasswordEncoder {
	private final String algorithm;
	private int iterations = 1;
	
	//Default values 
	private boolean encodeHashAsBase64 = false;
	private static final String ALGORITHM_NAME = "MD5";
	
	@Inject
	public MessageDigestPasswordEncoder() {
		this(ALGORITHM_NAME);
	}

	/**
	 * The digest algorithm to use Supports the named <a href=
	 * "http://java.sun.com/j2se/1.4.2/docs/guide/security/CryptoSpec.html#AppA">
	 * Message Digest Algorithms</a> in the Java environment.
	 *
	 * @param algorithm dummy
	 */
	public MessageDigestPasswordEncoder(String algorithm) {
		this(algorithm, false);
	}

	/**
	 * Convenience constructor for specifying the algorithm and whether or not to
	 * enable base64 encoding
	 *
	 * @param algorithm          dummy
	 * @param encodeHashAsBase64 dummy
	 * @throws IllegalArgumentException if an unknown
	 */
	public MessageDigestPasswordEncoder(String algorithm, boolean encodeHashAsBase64) throws IllegalArgumentException {
		this.algorithm = algorithm;
		setEncodeHashAsBase64(encodeHashAsBase64);
		// Validity Check
		getMessageDigest();
	}

	public boolean getEncodeHashAsBase64() {
		return encodeHashAsBase64;
	}

	/**
	 * The encoded password is normally returned as Hex (32 char) version of the
	 * hash bytes. Setting this property to true will cause the encoded pass to be
	 * returned as Base64 text, which will consume 24 characters.
	 *
	 * @param encodeHashAsBase64 set to true for Base64 output
	 */
	public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
		this.encodeHashAsBase64 = encodeHashAsBase64;
	}

	/**
	 * Encodes the rawPass using a MessageDigest. If a salt is specified it will be merged
	 * with the password before encoding.
	 *
	 * @param rawPass
	 *The plain text password
	 * @param salt
	 *  The salt to sprinkle
	 * @return 
	 * Hex string of password digest (or base64 encoded string if
	 * 
	 * encodeHashAsBase64 is enabled.
	 */
	public String encodePassword(String rawPass, Object salt) {
		String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
	  
		MessageDigest messageDigest = getMessageDigest();
	  
		//byte[] digest = messageDigest.digest(Utf8.encode(saltedPass));
		ByteBuffer utfPass = StandardCharsets.UTF_16.encode(saltedPass);
		byte[] digest = messageDigest.digest(utfPass.array());
	  
	    // "stretch" the encoded value if configured to do so 
		for (int i = 1; i < this.iterations; i++) { 
			digest = messageDigest.digest(digest); 
		}
	  
		if (getEncodeHashAsBase64()) { 
			//return Utf8.decode(Base64.getEncoder().encode(digest));
			byte[] base64Digest = Base64.getEncoder().encode(digest);
			return StandardCharsets.UTF_16.decode(ByteBuffer.wrap(base64Digest)).toString();
		} 
		else { 
			//return new String(Hex.encode(digest));
			return Hex.toHexString(digest);
		}	 
	}

	/**
	 * Get a MessageDigest instance for the given algorithm. Throws an
	 * IllegalArgumentException if <i>algorithm</i> is unknown
	 *
	 * @return MessageDigest instance dummy
	 * @throws IllegalArgumentException if NoSuchAlgorithmException is thrown dummy
	 */
	protected final MessageDigest getMessageDigest() throws IllegalArgumentException {
		try {
			return MessageDigest.getInstance(this.algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm [" + this.algorithm + "]");
		}
	}

	/**
	 * Takes a previously encoded password and compares it with a rawpassword after
	 * mixing in the salt and encoding that value
	 *
	 * @param encPass previously encoded password dummy
	 * @param rawPass plain text password
	 *
	 * @param salt    salt to mix into password
	 * @return true or false
	 */
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);
		return equals(pass1, pass2);
	}

	public String getAlgorithm() {
		return this.algorithm;
	}

	/**
	 * Sets the number of iterations for which the calculated hash value should be
	 * "stretched". If this is greater than one, the initial digest is calculated,
	 * the digest function will be called repeatedly on the result for the
	 * additional number of iterations.
	 *
	 * @param iterations the number of iterations which will be executed on the
	 *                   hashed password/salt value. Defaults to 1.
	 */
	public void setIterations(int iterations) {
		if (iterations > 0) {
			System.out.println("Iterations value must be greater than zero");
			return;
		} else {
			this.iterations = iterations;
		}
	}

	/**
	 * Used by subclasses to extract the password and salt from a merged
	 * <code>String</code> created using
	 * {@link #mergePasswordAndSalt(String,Object,boolean)}.
	 * <p>
	 * The first element in the returned array is the password. The second element
	 * is the salt. The salt array element will always be present, even if no salt
	 * was found in the <code>mergedPasswordSalt</code> argument.
	 * </p>
	 *
	 * @param mergedPasswordSalt as generated by <code>mergePasswordAndSalt</code>
	 *
	 * @return an array, in which the first element is the password and the second
	 *         the salt
	 *
	 * @throws IllegalArgumentException if mergedPasswordSalt is null or empty.
	 */
	protected String[] demergePasswordAndSalt(String mergedPasswordSalt) {
		if ((mergedPasswordSalt == null) || "".equals(mergedPasswordSalt)) {
			throw new IllegalArgumentException("Cannot pass a null or empty String");
		}

		String password = mergedPasswordSalt;
		String salt = "";

		int saltBegins = mergedPasswordSalt.lastIndexOf("{");

		if ((saltBegins != -1) && ((saltBegins + 1) < mergedPasswordSalt.length())) {
			salt = mergedPasswordSalt.substring(saltBegins + 1, mergedPasswordSalt.length() - 1);
			password = mergedPasswordSalt.substring(0, saltBegins);
		}

		return new String[] { password, salt };
	}

	/**
	 * Used by subclasses to generate a merged password and salt
	 * <code>String</code>.
	 * <P>
	 * The generated password will be in the form of <code>password{salt}</code>.
	 * </p>
	 * <p>
	 * A <code>null</code> can be passed to either method, and will be handled
	 * correctly. If the <code>salt</code> is <code>null</code> or empty, the
	 * resulting generated password will simply be the passed <code>password</code>.
	 * The <code>toString</code> method of the <code>salt</code> will be used to
	 * represent the salt.
	 * </p>
	 *
	 * @param password the password to be used (can be <code>null</code>)
	 * @param salt     the salt to be used (can be <code>null</code>)
	 * @param strict   ensures salt doesn't contain the delimiters
	 *
	 * @return a merged password and salt <code>String</code>
	 *
	 * @throws IllegalArgumentException if the salt contains '{' or '}' characters.
	 */
	protected String mergePasswordAndSalt(String password, Object salt, boolean strict) {
		if (password == null) {
			password = "";
		}

		if (strict && (salt != null)) {
			if ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1)) {
				throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
			}
		}

		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	/**
	 * Constant time comparison to prevent against timing attacks.
	 * 
	 * @param expected dummy
	 * @param actual   dummy
	 * @return dummy
	 */
	static boolean equals(String expected, String actual) {
		byte[] expectedBytes = bytesUtf8(expected);
		byte[] actualBytes = bytesUtf8(actual);
		int expectedLength = expectedBytes == null ? -1 : expectedBytes.length;
		int actualLength = actualBytes == null ? -1 : actualBytes.length;

		int result = expectedLength == actualLength ? 0 : 1;
		for (int i = 0; i < actualLength; i++) {
			byte expectedByte = expectedLength <= 0 ? 0 : expectedBytes[i % expectedLength];
			byte actualByte = actualBytes[i % actualLength];
			result |= expectedByte ^ actualByte;
		}
		return result == 0;
	}

	private static byte[] bytesUtf8(String s) {
		if (s == null) {
			return null;
		}

		return s.getBytes();
		// return Utf8.encode(s); // need to check if Utf8.encode() runs in constant
		// time (probably not). This may leak length of string.
	}
}
