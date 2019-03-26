package cropcert.user.signup;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class SignupModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Signup.class).in(Scopes.SINGLETON);
	}
}
