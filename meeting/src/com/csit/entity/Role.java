package com.csit.entity;
/**
 * @Description: 用户角色
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-12
 * @author lhy
 * @vesion 1.0
 */
public class Role {
	public static String SESSION_LOGIN_ROLE = "role";
	public static String ADMIN = "admin";
	public static String NORMAL = "normal";
	/**
	 * 角色Id
	 */
	private Integer roleId;
	/**
	 * 角色名称
	 */
	public String roleName;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
