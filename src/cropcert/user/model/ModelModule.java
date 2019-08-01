package cropcert.user.model;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ModelModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(Admin.class).in(Scopes.SINGLETON);
		bind(CollectionCenterPerson.class).in(Scopes.SINGLETON);
		bind(CollectionCenter.class).in(Scopes.SINGLETON);
		bind(CoOperative.class).in(Scopes.SINGLETON);
		bind(CoOperativePerson.class).in(Scopes.SINGLETON);
		bind(FactoryPerson.class).in(Scopes.SINGLETON);
		bind(Farmer.class).in(Scopes.SINGLETON);
		bind(Union.class).in(Scopes.SINGLETON);
		bind(UnionPerson.class).in(Scopes.SINGLETON);
		bind(User.class).in(Scopes.SINGLETON);
		bind(CropcertEntity.class).in(Scopes.SINGLETON);
	}
}
