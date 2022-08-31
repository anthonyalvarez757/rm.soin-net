package com.soin.sgrm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soin.sgrm.dao.RequestRM_P1_R4Dao;
import com.soin.sgrm.dao.RequestRM_P1_R5Dao;
import com.soin.sgrm.model.RequestRM_P1_R4;
import com.soin.sgrm.model.RequestRM_P1_R5;

@Service("RequestRM_P1_R5Service")
@Transactional("transactionManager")
public class RequestRM_P1_R5ServiceImpl implements RequestRM_P1_R5Service{
	@Autowired
	RequestRM_P1_R5Dao dao; 

	@Override
	public RequestRM_P1_R5 findById(Long id) {
		return dao.getById(id);
	}

	@Override
	public RequestRM_P1_R5 findByKey(String name, String value) {
		return dao.getByKey(name, value);
	}

	@Override
	public List<RequestRM_P1_R5> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(RequestRM_P1_R5 model) {
		dao.save(model);
	}

	@Override
	public void delete(Long id) {
		RequestRM_P1_R5 model=findById(id);
		dao.delete(model);
	}

	@Override
	public void update(RequestRM_P1_R5 model) {
		dao.update(model);
	}
	
	@Override
	public RequestRM_P1_R5 requestRm5(Long id){
		return dao.requestRM5(id);
	}

}
