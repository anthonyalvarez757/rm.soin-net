package com.soin.sgrm.service.pos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soin.sgrm.dao.pos.TypeChangeDao;
import com.soin.sgrm.model.pos.PTypeChange;

@Service("typeChangeService")
@Transactional("transactionManagerPos")
public class TypeChangeServiceImpl implements TypeChangeService{
	
	@Autowired
	TypeChangeDao dao;
	@Override
	public PTypeChange findById(Long id) {
		return dao.getById(id);
	}

	@Override
	public PTypeChange findByKey(String name, String value) {
		return dao.getByKey(name, value);
	}

	@Override
	public List<PTypeChange> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(PTypeChange model) {
		dao.save(model);
	}

	@Override
	public void delete(Long id) {
		PTypeChange model= findById(id);
		dao.delete(model);
	}

	@Override
	public void update(PTypeChange model) {
		dao.update(model);
	}

}
