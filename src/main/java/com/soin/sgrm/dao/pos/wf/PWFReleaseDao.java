package com.soin.sgrm.dao.pos.wf;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.soin.sgrm.model.pos.wf.PWFRelease;
import com.soin.sgrm.utils.JsonSheet;

public interface PWFReleaseDao {

	List<PWFRelease> list();

	PWFRelease findWFReleaseById(Integer id);
	
	JsonSheet<?> listWorkFlowRelease(String name, int sEcho, int iDisplayStart, int iDisplayLength, String sSearch,
			String[] filtred, String[] dateRange, Integer systemId, Integer statusId) throws SQLException, ParseException;
	
	JsonSheet<?> listWorkFlowManager(String name, int sEcho, int iDisplayStart, int iDisplayLength, String sSearch,
			String[] filtred, String[] dateRange, Integer systemId, Integer statusId, Object[] systemsId, Integer idUser) throws SQLException, ParseException;
	
	void wfStatusRelease(PWFRelease release);
	
	Integer countByType(String group, Object[] ids, Integer idUser);

	void wfStatusReleaseWithOutMin(PWFRelease release);

}
