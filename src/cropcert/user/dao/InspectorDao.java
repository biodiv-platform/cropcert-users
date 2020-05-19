package cropcert.user.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.model.Inspector;

public class InspectorDao extends AbstractDao<Inspector, Long>{

	@Inject
	protected InspectorDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Inspector findById(Long id) {
		Session session = sessionFactory.openSession();
		Inspector entity = null;
		try {
			entity = session.get(Inspector.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
