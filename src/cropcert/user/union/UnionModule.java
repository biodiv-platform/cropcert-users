package cropcert.user.union;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class UnionModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(UnionDao.class).in(Scopes.SINGLETON);
		bind(UnionService.class).in(Scopes.SINGLETON);
		bind(UnionEndPoint.class).in(Scopes.SINGLETON);
	}
}
