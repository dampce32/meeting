package com.csit.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.csit.dao.RoleDao;
import com.csit.entity.Role;
import com.csit.util.JdbcUtils;
/**
 * @Description: 角色DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class RoleDaoImpl implements RoleDao{
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.RoleDao#queryById(java.lang.Integer)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Role queryById(Integer id) throws SQLException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from T_Role where roleId = ?";
		Object params[] ={id};
		Role model = (Role) runner.query(sql, params, new BeanHandler(Role.class));
		return model;
	}

}
