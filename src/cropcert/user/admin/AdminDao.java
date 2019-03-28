package cropcert.user.admin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.common.AbstractDao;

public class AdminDao extends AbstractDao<Admin, Long>{

	@Inject
	protected AdminDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Admin findById(Long id) {
		Session session = sessionFactory.openSession();
		Admin entity = null;
		try {
			entity = session.get(Admin.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
