package com.soin.sgrm.dao;

import com.soin.sgrm.model.RequestBase;

public interface RequestBaseDao extends BaseDao<Long, RequestBase> {

	Integer existNumRequest(String numRequest);

	Integer countByManager(Integer id, Long idRequest);

	Integer countByType(Integer id, String type, int query, Object[] ids); 

}
