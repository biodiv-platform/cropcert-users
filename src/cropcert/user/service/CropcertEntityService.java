package cropcert.user.service;

import com.google.inject.Inject;

import cropcert.user.dao.CropcertEntityDao;
import cropcert.user.model.CropcertEntity;


public class CropcertEntityService extends AbstractService<CropcertEntity>{

	@Inject
	public CropcertEntityService(CropcertEntityDao cropcertEntityDao) {
		super(cropcertEntityDao);
	}
}
