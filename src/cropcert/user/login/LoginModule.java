package cropcert.user.login;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class LoginModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Signup.class).in(Scopes.SINGLETON);
		bind(LoginEndPoint.class).in(Scopes.SINGLETON);
	}
}
