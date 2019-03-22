package cropcert.user.farmer;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class FarmerModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(FarmerDao.class).in(Scopes.SINGLETON);
		bind(FarmerService.class).in(Scopes.SINGLETON);
		bind(FarmerEndPoint.class).in(Scopes.SINGLETON);
	}
}
