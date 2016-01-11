package com.csit.service.impl;

import java.sql.SQLException;

import com.csit.dao.RoleDao;
import com.csit.entity.Role;
import com.csit.factory.DaoFactory;
import com.csit.service.RoleService;
/**
 * @Description:角色Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class RoleServiceImpl implements RoleService{
	private RoleDao roleDao = DaoFactory.getInstance().createDao(RoleDao.class);
	/*
	 * (non-Javadoc)   
	 * @see com.csit.service.RoleService#queryById(java.lang.Integer)
	 */
	@Override
	public Role queryById(Integer roleId) {
		try {
			return roleDao.queryById(roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
