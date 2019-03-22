package cropcert.user.farmer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.common.AbstractDao;

public class FarmerDao extends AbstractDao<Farmer, Long>{

	@Inject
	protected FarmerDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Farmer findById(Long id) {
		Session session = sessionFactory.openSession();
		Farmer entity = null;
		try {
			entity = session.get(Farmer.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
