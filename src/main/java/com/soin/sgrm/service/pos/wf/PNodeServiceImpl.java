package com.soin.sgrm.service.pos.wf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soin.sgrm.dao.pos.wf.PNodeDao;
import com.soin.sgrm.dao.wf.NodeDao;
import com.soin.sgrm.model.Incidence;
import com.soin.sgrm.model.RFC;
//import com.soin.sgrm.model.Incidence;
import com.soin.sgrm.model.Release;
import com.soin.sgrm.model.pos.PRelease;
import com.soin.sgrm.model.pos.wf.PNode;
import com.soin.sgrm.model.wf.NodeIncidence;
//import com.soin.sgrm.model.wf.NodeIncidence;
import com.soin.sgrm.model.wf.NodeRFC;

@Transactional("transactionManagerPos")
@Service("PNodeService")
public class PNodeServiceImpl implements PNodeService {
	
	@Autowired
	PNodeDao dao;

	@Override
	public List<PNode> list() {
		return dao.list();
	}

	@Override
	public PNode findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public PNode save(PNode node) {
		return dao.save(node);
	}

	@Override
	public PNode update(PNode node) {
		return dao.update(node);
	}

	@Override
	public void delete(Integer id) throws Exception {
		dao.delete(id);
	}

	@Override
	public PNode existWorkFlow(PRelease release) {
		return dao.existWorkFlow(release);
	}

	@Override
	public NodeIncidence saveNodeIncidence(NodeIncidence node) {
		
		return dao.saveNodeIncidence(node);
	}

	@Override
	public List<NodeIncidence> listNodeIncidence() {
		return dao.listNodeIncidence();
	}

	@Override
	public NodeIncidence findByIdNoInci(Integer id) {
		return dao.findByIdNoInci(id);
	}

	@Override
	public NodeIncidence updateNodeIncidence(NodeIncidence node) {
		return dao.updateNodeIncidence(node);
	}

	@Override
	public void deleteNodeIncidence(Integer id) throws Exception {
		dao.deleteNodeIncidence(id);
	}

	@Override
	public NodeIncidence existWorkFlowNodeIn(Incidence incidence) {
		return dao.existWorkFlowNodeIn(incidence);
	}
		@Override
	public boolean verifyStartNodeIncidence(NodeIncidence node) {
		// TODO Auto-generated method stub
		return dao.verifyStartNodeIncidence(node);
	}

	@Override
	public boolean verifyStartNode(PNode node) {
		
		return dao.verifyStartNode(node);
	}

	@Override
	public NodeRFC saveNodeRFC(NodeRFC node) {
		return dao.saveNodeRFC(node);
	}

	@Override
	public NodeRFC updateNodeRFC(NodeRFC node) {
		return dao.updateNodeRFC(node);
	}

	@Override
	public List<NodeRFC> listNodeRFC() {
		return dao.listNodeRFC();
	}

	@Override
	public NodeRFC findByIdNoRFC(Integer id) {
		
		return dao.findByIdNoRFC(id);
	}

	@Override
	public void deleteNodeRFC(Integer id) throws Exception {
		dao.deleteNodeRFC(id);
	}

	@Override
	public NodeRFC existWorkFlowNodeRFC(RFC rfc) {
		// TODO Auto-generated method stub
		return dao.existWorkFlowNodeRFC(rfc);
	}

	@Override
	public boolean verifyStartNodeRFC(NodeRFC node) {
		return dao.verifyStartNodeRFC(node);
	}



}