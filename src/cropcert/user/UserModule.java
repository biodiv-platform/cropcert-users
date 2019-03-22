package cropcert.user;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class UserModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(UserDao.class).in(Scopes.SINGLETON);
		bind(CollectionService.class).in(Scopes.SINGLETON);
		bind(UserEndPoint.class).in(Scopes.SINGLETON);
	}
}
