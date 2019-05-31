package cropcert.user.factoryperson;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.common.AbstractDao;

public class FactoryPersonDao extends AbstractDao<FactoryPerson, Long>{

	@Inject
	protected FactoryPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public FactoryPerson findById(Long id) {
		Session session = sessionFactory.openSession();
		FactoryPerson entity = null;
		try {
			entity = session.get(FactoryPerson.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
