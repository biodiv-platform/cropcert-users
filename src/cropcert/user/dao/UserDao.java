package cropcert.user.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.model.User;

public class UserDao extends AbstractDao<User, Long>{

	@Inject
	protected UserDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public User findById(Long id) {
		Session session = sessionFactory.openSession();
		User entity = null;
		try {
			entity = session.get(User.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
