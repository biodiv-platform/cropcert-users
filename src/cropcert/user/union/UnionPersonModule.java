package cropcert.user.union;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class UnionPersonModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(UnionPersonDao.class).in(Scopes.SINGLETON);
		bind(UnionPersonService.class).in(Scopes.SINGLETON);
		bind(UnionPersonEndPoint.class).in(Scopes.SINGLETON);
	}
}
