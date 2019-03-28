package cropcert.user.ccperson;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.common.AbstractDao;

public class CCPersonDao extends AbstractDao<CCPerson, Long>{

	@Inject
	protected CCPersonDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CCPerson findById(Long id) {
		Session session = sessionFactory.openSession();
		CCPerson entity = null;
		try {
			entity = session.get(CCPerson.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
