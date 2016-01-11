package com.csit.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.csit.dao.DepartmentDao;
import com.csit.dao.UserDao;
import com.csit.entity.Department;
import com.csit.entity.User;
import com.csit.factory.DaoFactory;
import com.csit.service.UserService;
import com.csit.util.Md5Util;
import com.csit.util.Msg;
import com.csit.util.Permission;
import com.csit.util.ServiceResult;
/**
 * @Description:用户Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class UserServiceImpl implements UserService {
	private UserDao userDao = DaoFactory.getInstance().createDao(UserDao.class);
	private DepartmentDao departmentDao = DaoFactory.getInstance().createDao(DepartmentDao.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.UserService#query(com.csit.entity.User)
	 */
	@Override
	public ServiceResult login(User user) {
		ServiceResult result = new ServiceResult(false);
		if (user.getUserCode() == null || "".equals(user.getUserCode().trim())) {
			result.setMessage(Msg.PLEASE_INPUT_USERCODE);
			return result;
		}
		if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
			result.setMessage(Msg.PLEASE_INPUT_PASSWORD);
			return result;
		}
		try {
			user.setPassword(Md5Util.parse(user.getPassword()));
			User model = userDao.query(user);
			if(model != null){
				result.setSuccess(true);
				result.setObject(model);
				return result;
			}else{
				result.setMessage(Msg.LOGIN_ERROR);
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.UserService#add(com.csit.entity.User)
	 */
	@Override
	public ServiceResult add(User user) {
		ServiceResult result = new ServiceResult(false);
		if (user.getName() == null || "".equals(user.getName())) {
			result.setMessage(Msg.PLEASE_INPUT_USER_NAME);
			return result;
		}
		if (user.getGender() == null || "".equals(user.getGender())) {
			result.setMessage(Msg.PLEASE_SELECT_GENDER);
			return result;
		}
		if (user.getDepartmentId() == null || "".equals(user.getDepartmentId())) {
			result.setMessage(Msg.PLEASE_SELECT_DEPARTMENT);
			return result;
		}
		Department department = departmentDao.queryById(user.getDepartmentId());
		if(department == null){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		if (user.getRoleId() == null || "".equals(user.getRoleId())) {
			result.setMessage(Msg.PLEASE_SELECT_ROLE);
			return result;
		} 
		if (user.getPhoneNumber() == null || "".equals(user.getPhoneNumber())) {
			result.setMessage(Msg.PLEASE_INPUT_PHONE);
			return result;
		}
		String password = Md5Util.parse(Msg.DEFAULT_PASSWORD);
		user.setPassword(password);
		boolean exist;
		try {
			exist = userDao.existByUserCode(user.getUserCode());
			if (!exist) {
				result.setMessage(Msg.USER_EXIST);
				return result;
			} else {
				Integer count = userDao.add(user);
				if (count == 0) {
					result.setMessage(Msg.PROGRAM_ERROR);
					return result;
				}else{
					result.setSuccess(true);
					result.setMessage(Msg.ADD_SUCCESS);
					return result;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
	}

	@Override
	public List<User> getList(Integer rows,Integer page) {
		try {
			return userDao.getList(rows,page);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Permission(Permission.ADMIN)
	public Long getTotal() {
		return userDao.getTotal();
	}

	@Override
	@Permission(Permission.ADMIN)
	public ServiceResult remove(Integer userId) {
		ServiceResult result = new ServiceResult(false);
		if(userId == null){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		Integer count = userDao.remove(userId);
		if(count == 0){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		result.setSuccess(true);
		result.setMessage(Msg.DELETE_SUCCESS);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.UserService#update(com.csit.entity.User)
	 */
	@Override
	@Permission(Permission.ADMIN)
	public ServiceResult update(User user) {
		ServiceResult result = new ServiceResult(false);
		if (user.getName() == null || "".equals(user.getName())) {
			result.setMessage(Msg.PLEASE_INPUT_USER_NAME);
			return result;
		}
		if (user.getGender() == null || "".equals(user.getGender())) {
			result.setMessage(Msg.PLEASE_SELECT_GENDER);
			return result;
		}
		if (user.getDepartmentId() == null || "".equals(user.getDepartmentId())) {
			result.setMessage(Msg.PLEASE_SELECT_DEPARTMENT);
			return result;
		}
		Department department = departmentDao.queryById(user.getDepartmentId());
		if(department == null){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		if (user.getRoleId() == null || "".equals(user.getRoleId())) {
			result.setMessage(Msg.PLEASE_SELECT_ROLE);
			return result;
		} 
		if (user.getPhoneNumber() == null || "".equals(user.getPhoneNumber())) {
			result.setMessage(Msg.PLEASE_INPUT_PHONE);
			return result;
		}
		User model = userDao.queryById(user.getUserId());
		if(model == null){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		model.setAddress(user.getAddress());
		model.setDepartmentId(user.getDepartmentId());
		model.setFax(user.getFax());
		model.setGender(user.getGender());
		model.setMailNumber(user.getMailNumber());
		model.setName(user.getName());
		model.setNote(user.getNote());
		model.setQq(user.getQq());
		model.setPhoneNumber(user.getPhoneNumber());
		model.setRoleId(user.getRoleId());
	    Integer count = userDao.update(model);
	    if(count == 0){
	    	result.setMessage(Msg.PROGRAM_ERROR);
			return result;
	    }else{
	    	result.setSuccess(true);
	    	result.setMessage(Msg.UPDATE_SUCCESS);
	    	return result;
	    }
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.UserService#resetPasswrod(java.lang.String)
	 */
	@Override
	@Permission(Permission.ADMIN)
	public ServiceResult resetPasswrod(Integer userId) {
		ServiceResult result = new ServiceResult(false);
		if(userId == null || "".equals(userId)){
			result.setMessage(Msg.PLEASE_SELECT_DATA);
			return result;
		}
		User user = userDao.queryById(userId);
		if(user == null ){
			result.setMessage(Msg.PLEASE_SELECT_DATA);
			return result;
		}
		user.setPassword(Md5Util.parse(Msg.DEFAULT_PASSWORD));
		Integer count = userDao.resetPassword(user);
		if(count == 0){
			result.setMessage(Msg.PROGRAM_ERROR);
		}else{
			result.setSuccess(true);
			result.setMessage(Msg.OPERATION_SUCCESS+",新密码为"+Msg.DEFAULT_PASSWORD);
		}
		return result;
	}

	@Override
	public ServiceResult personal(User user) {
		ServiceResult result = new ServiceResult(false);
		if (user.getName() == null || "".equals(user.getName())) {
			result.setMessage(Msg.PLEASE_INPUT_USER_NAME);
			return result;
		}
		if (user.getGender() == null || "".equals(user.getGender())) {
			result.setMessage(Msg.PLEASE_SELECT_GENDER);
			return result;
		}
		if (user.getDepartmentId() == null || "".equals(user.getDepartmentId())) {
			result.setMessage(Msg.PLEASE_SELECT_DEPARTMENT);
			return result;
		}
		if (user.getPhoneNumber() == null || "".equals(user.getPhoneNumber())) {
			result.setMessage(Msg.PLEASE_INPUT_PHONE);
			return result;
		}
		User model = userDao.queryById(user.getUserId());
		if(model == null){
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		model.setAddress(user.getAddress());
		model.setDepartmentId(user.getDepartmentId());
		model.setFax(user.getFax());
		model.setGender(user.getGender());
		model.setMailNumber(user.getMailNumber());
		model.setName(user.getName());
		model.setNote(user.getNote());
		model.setQq(user.getQq());
		model.setPhoneNumber(user.getPhoneNumber());
	    Integer count = userDao.update(model);
	    if(count == 0){
	    	result.setMessage(Msg.PROGRAM_ERROR);
			return result;
	    }else{
	    	result.setSuccess(true);
	    	result.setMessage(Msg.UPDATE_SUCCESS);
	    	return result;
	    }
	}
}
