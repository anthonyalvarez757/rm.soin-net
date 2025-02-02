package com.soin.sgrm.dao.pos;

import java.util.List;

import com.soin.sgrm.model.pos.PSystem;

public interface SystemDao extends BaseDao<Long, PSystem> {

	List<PSystem> listWithProject();
	List<PSystem> listProjects(Long id);
	List<PSystem> listSystemByName(String[] names);
}
