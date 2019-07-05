package cropcert.user.union;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.common.AbstractDao;

public class UnionDao extends AbstractDao<Union, Long>{

	@Inject
	protected UnionDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Union findById(Long id) {
		Session session = sessionFactory.openSession();
		Union entity = null;
		try {
			entity = session.get(Union.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
