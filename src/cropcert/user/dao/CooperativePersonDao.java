package cropcert.user.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.model.CooperativePerson;

public class CooperativePersonDao extends AbstractDao<CooperativePerson, Long>{

	@Inject
	protected CooperativePersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CooperativePerson findById(Long id) {
		Session session = sessionFactory.openSession();
		CooperativePerson entity = null;
		try {
			entity = session.get(CooperativePerson.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
