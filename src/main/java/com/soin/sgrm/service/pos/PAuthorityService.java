package com.soin.sgrm.service.pos;

import java.util.List;

import com.soin.sgrm.model.Authority;
import com.soin.sgrm.model.pos.PAuthority;

public interface PAuthorityService {

	List<PAuthority> list();

	PAuthority findById(Integer id);

	void save(PAuthority authority);

	void update(PAuthority authority);

	void delete(Integer id);

	PAuthority findByName(String name);

}
