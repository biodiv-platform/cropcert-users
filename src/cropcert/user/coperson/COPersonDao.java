package cropcert.user.coperson;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.common.AbstractDao;

public class COPersonDao extends AbstractDao<COPerson, Long>{

	@Inject
	protected COPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public COPerson findById(Long id) {
		Session session = sessionFactory.openSession();
		COPerson entity = null;
		try {
			entity = session.get(COPerson.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
