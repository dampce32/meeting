package com.csit.entity;

import java.io.Serializable;

/**
 * 用户
 * @Description:
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-12
 * @author lhy
 * @vesion 1.0
 */
public class User implements Serializable{
	private static final long serialVersionUID = 5506468018255193190L;
	/**
	 * SESSION中的USER对象
	 */
	public static String SESSION_LOGIN_USER ="login_user";
	/**
	 * SESSION中的USER的权限
	 */
	public static String SESSION_LOGIN_POWER ="login_power";
	/**
	 * id 自增
	 */
	private Integer userId;
	/**
	 * 用户名
	 */
	private String userCode;
	/**
	 * 密码 (MD5)
	 */
	private String password;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 电话号码
	 */
	private String phoneNumber;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 部门Id
	 */
	private Integer departmentId;
	/**
	 * 部门名称
	 */
	private String departmentName;
	/**
	 * 邮政编码
	 */
	private String mailNumber;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * qq号码
	 */
	private String qq;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 角色Id
	 */
	private Integer roleId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getMailNumber() {
		return mailNumber;
	}
	public void setMailNumber(String mailNumber) {
		this.mailNumber = mailNumber;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
