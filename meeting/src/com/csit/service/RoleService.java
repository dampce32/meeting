package com.csit.service;

import com.csit.entity.Role;
/**
 * @Description: 角色Service
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public interface RoleService {
	/**
	 * @Description: 根据roleId 得到Role 对象
	 * @Create: 2014-2-20 下午4:24:11
	 * @author lhy
	 * @update logs
	 * @param roleId
	 * @return
	 */
	Role queryById(Integer roleId);

}
