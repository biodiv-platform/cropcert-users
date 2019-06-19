package cropcert.user.coperson;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class COPersonModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(COPersonDao.class).in(Scopes.SINGLETON);
		bind(COPersonService.class).in(Scopes.SINGLETON);
		bind(COPersonEndPoint.class).in(Scopes.SINGLETON);
	}
}
