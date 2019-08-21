package cropcert.user.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.model.Cooperative;

public class CooperativeDao extends AbstractDao<Cooperative, Long>{

	@Inject
	protected CooperativeDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Cooperative findById(Long id) {
		Session session = sessionFactory.openSession();
		Cooperative entity = null;
		try {
			entity = session.get(Cooperative.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
