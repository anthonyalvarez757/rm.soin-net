package com.soin.sgrm.service.pos.wf;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soin.sgrm.dao.pos.wf.PWFIncidenceDao;
import com.soin.sgrm.model.pos.wf.PWFIncidence;
import com.soin.sgrm.utils.JsonSheet;

@Transactional("transactionManagerPos")
@Service("PWFIncidenceService")
public class PWFIncidenceServiceImpl implements PWFIncidenceService {

	@Autowired
	private PWFIncidenceDao dao;

	@Override
	public List<PWFIncidence> list() {
		return dao.list();
	}

	@Override
	public PWFIncidence findWFIncidenceById(Long id) {
		return dao.findWFIncidenceById(id);
	}

	@Override
	public JsonSheet<?> listWorkFlowIncidence(String name, int sEcho, int iDisplayStart, int iDisplayLength,
			String sSearch, String[] filtred, String[] dateRange, Integer systemId, Long statusId)
			throws SQLException, ParseException {
		return dao.listWorkFlowIncidence(name, sEcho, iDisplayStart, iDisplayLength, sSearch, filtred, dateRange,
				systemId, statusId);
	}

	@Override
	public void wfStatusIncidence(PWFIncidence incidence) {
		dao.wfStatusIncidence(incidence);
	}

	@Override
	public JsonSheet<?> listWorkFlowManager(String name, int sEcho, int iDisplayStart, int iDisplayLength,
			String sSearch, String[] filtred, String[] dateRange, Integer systemId, Long statusId,
			Object[] systemsId) throws SQLException, ParseException {
		return dao.listWorkFlowManager(name, sEcho, iDisplayStart, iDisplayLength, sSearch, filtred, dateRange, systemId, statusId, systemsId);
	}

	@Override
	public Integer countByType(String group, Object[] ids) {
		return dao.countByType(group, ids);
	}


}
