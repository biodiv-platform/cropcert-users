package cropcert.user.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.model.CoOperativePerson;

public class CoOperativePersonDao extends AbstractDao<CoOperativePerson, Long>{

	@Inject
	protected CoOperativePersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CoOperativePerson findById(Long id) {
		Session session = sessionFactory.openSession();
		CoOperativePerson entity = null;
		try {
			entity = session.get(CoOperativePerson.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
