package com.csit.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.csit.dao.DepartmentDao;
import com.csit.entity.Department;
import com.csit.factory.DaoFactory;
import com.csit.service.DepartmentService;
import com.csit.util.Msg;
import com.csit.util.ServiceResult;

public class DepartmentServiceImpl implements DepartmentService{
	private DepartmentDao departmentDao = DaoFactory.getInstance().createDao(DepartmentDao.class);

	@Override
	public ServiceResult update(Department department) {
		ServiceResult result = new ServiceResult(false);
		if (department.getDepartmentCode() == null || "".equals(department.getDepartmentCode())) {
			result.setMessage(Msg.PLEASE_INPUT_DEPARTMENTCODE);
			return result;
		}
		if (department.getDepartmentName() == null || "".equals(department.getDepartmentName())) {
			result.setMessage(Msg.PLEASE_INPUT_DEPARTMENT_NAME);
			return result;
		}
		
		Department model = departmentDao.queryById(department.getId());
		if(model == null){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		model.setDepartmentCode(department.getDepartmentCode());
		model.setDepartmentName(department.getDepartmentName());
		
	    Integer count = departmentDao.update(model);
	    if(count == 0){
	    	result.setMessage(Msg.PROGRAM_ERROR);
			return result;
	    }else{
	    	result.setSuccess(true);
	    	result.setMessage(Msg.UPDATE_SUCCESS);
	    	return result;
	    }
	}

	@Override
	public ServiceResult add(Department department) {
		ServiceResult result = new ServiceResult(false);
		if (department.getDepartmentCode() == null || "".equals(department.getDepartmentCode())) {
			result.setMessage(Msg.PLEASE_INPUT_DEPARTMENTCODE);
			return result;
		}
		if (department.getDepartmentName() == null || "".equals(department.getDepartmentName())) {
			result.setMessage(Msg.PLEASE_INPUT_DEPARTMENT_NAME);
			return result;
		}
		
		boolean exist = false;
		exist = departmentDao.existByCode(department.getDepartmentCode());
		if (!exist) {
			result.setMessage(Msg.DEPARTMENT_EXIST);
			return result;
		} else {
			Integer count = departmentDao.add(department);
			if (count == 0) {
				result.setMessage(Msg.PROGRAM_ERROR);
				return result;
			}else{
				result.setSuccess(true);
				result.setMessage(Msg.ADD_SUCCESS);
				return result;
			}
		}
	}

	@Override
	public List<Department> getList(Integer rows, Integer page) {
		return departmentDao.getList(rows,page);
	}

	@Override
	public Long getTotal() {
		return departmentDao.getTotal();
	}

	@Override
	public ServiceResult remove(Integer id) {
		ServiceResult result = new ServiceResult(false);
		if(id == null){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		Integer count = 0;
		try {
			count = departmentDao.remove(id);
		} catch (SQLException e) {
			result.setMessage(Msg.DEPARTMENT_USED);
			return result;
		}
		if(count == 0){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		result.setSuccess(true);
		result.setMessage(Msg.DELETE_SUCCESS);
		return result;
	}

	@Override
	public List<Department> queryAll() {
		return departmentDao.queryAll();
	}
}
