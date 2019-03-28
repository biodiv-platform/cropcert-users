package cropcert.user.admin;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AdminModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(AdminDao.class).in(Scopes.SINGLETON);
		bind(AdminService.class).in(Scopes.SINGLETON);
		bind(AdminEndPoint.class).in(Scopes.SINGLETON);
	}
}
