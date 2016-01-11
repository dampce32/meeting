package com.csit.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.csit.dao.PowerDao;
import com.csit.entity.Power;
import com.csit.factory.DaoFactory;
import com.csit.service.PowerService;
import com.csit.util.Msg;
import com.csit.util.Permission;
import com.csit.util.ServiceResult;
import com.csit.util.TreeBaseUtil;
import com.csit.util.TreeNode;
/**
 * @Description:权限Service 实现类
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class PowerServiceImpl implements PowerService{
	private PowerDao powerDao = DaoFactory.getInstance().createDao(PowerDao.class);
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PowerService#getPowerByRoleId(java.lang.Integer)
	 */
	public  List<Power> getPowerByRoleId(Integer roleId){
		try {
			return powerDao.getPowerByRoleId(roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PowerService#getList(java.lang.Integer, java.lang.Integer)
	 */
	@Permission("ADMIN")
	public List<Power> getList(Integer pid) {
		Power parent = powerDao.queryById(pid);
		return  powerDao.getChild(parent);
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PowerService#getTotal()
	 */
	@Permission("ADMIN")
	@Override
	public Long getTotal() {
		return powerDao.getTotal();
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.PowerService#queryNode()
	 */
	@Permission("ADMIN")
	@Override
	public String queryNode(Integer rid) {
		List<Power> powers = powerDao.queryNode(rid);
		
		TreeNode node = new TreeNode();
		for(Power power : powers ){
			if(power.getDeepth()!=null && power.getDeepth() == 2){
				node.setText(power.getPowerName());
				node.setId(power.getPowerId());
			}else if(power.getDeepth()!=null && power.getDeepth() == 3){
				TreeNode child = new TreeNode();
				child.setText(power.getPowerName());
				child.setId(power.getPowerId());
				node.getChildren().add(child);
			}
		}
		return TreeBaseUtil.toJSON(node);
	}
	@Permission(Permission.ADMIN)
	@Override
	public ServiceResult add(Power power,Integer pid) {
		ServiceResult result = new ServiceResult(false);
		Power parent = powerDao.queryById(pid);
		power.setLft(parent.getRgt());
		power.setRgt(parent.getRgt()+1);
		power.setRoleId(parent.getRoleId());
		powerDao.add(power);
		result.setSuccess(true);
		result.setMessage(Msg.ADD_SUCCESS);
		return result;
	}
	@Override
	public Power queryById(Integer pid) {
		return powerDao.queryById(pid);
	}
	@Permission(Permission.ADMIN)
	@Override
	public ServiceResult delete(Integer id) {
		ServiceResult result = new ServiceResult(false);
		if (id == null) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		Power power = queryById(id);
		if(power == null){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		
		Integer count = powerDao.remove(id,power.getLft(),power.getRgt());
		if (count == 0) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		result.setSuccess(true);
		result.setMessage(Msg.DELETE_SUCCESS);
		return result;
	}
	@Permission(Permission.ADMIN)
	@Override
	public ServiceResult update(Power power) {
		ServiceResult result = new ServiceResult(false);
		if (power.getPowerName() == null || "".equals(power.getPowerName() )) {
			result.setMessage(Msg.PLEASE_INPUT_POWER_NAME);
			return result;
		}
		if (power.getUrl() == null || "".equals(power.getUrl())) {
			result.setMessage(Msg.PLEASE_INPUT_URL);
			return result;
		}
		Power model = powerDao.queryById(power.getPowerId());
		if (model == null) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		model.setPowerName(power.getPowerName());
		model.setUrl(power.getUrl());
		Integer count = powerDao.update(model);
		if (count == 0) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		} else {
			result.setSuccess(true);
			result.setMessage(Msg.UPDATE_SUCCESS);
			return result;
		}
	}
}
