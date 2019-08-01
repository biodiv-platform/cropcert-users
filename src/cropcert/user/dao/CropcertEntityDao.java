package cropcert.user.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.user.model.CropcertEntity;

public class CropcertEntityDao extends AbstractDao<CropcertEntity, Long>{

	@Inject
	protected CropcertEntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public CropcertEntity findById(Long id) {
		Session session = sessionFactory.openSession();
		CropcertEntity entity = null;
		try {
			entity = session.get(CropcertEntity.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

}
