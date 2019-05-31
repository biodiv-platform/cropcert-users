package cropcert.user.factoryperson;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class FactoryPersonModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(FactoryPersonDao.class).in(Scopes.SINGLETON);
		bind(FactoryPersonService.class).in(Scopes.SINGLETON);
		bind(FactoryPersonEndPoint.class).in(Scopes.SINGLETON);
	}
}
