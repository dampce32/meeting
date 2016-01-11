package com.csit.dao;

import java.sql.SQLException;

import com.csit.entity.Role;

/**
 * @Description:角色DAO
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public interface RoleDao {
	/**
	 * @Description: 根据用户角色编号得到Role对象
	 * @Create: 2014-2-12 下午5:17:26
	 * @author lhy
	 * @update logs
	 * @param roelId
	 * @return
	 */
	Role queryById(Integer id) throws SQLException;
	
}
