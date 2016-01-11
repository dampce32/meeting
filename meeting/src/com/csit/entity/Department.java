package com.csit.entity;

import java.io.Serializable;
/**
 * @Description: 部门
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-3-6
 * @author lhy
 * @vesion 1.0
 */
public class Department implements Serializable{
	private static final long serialVersionUID = 8502956733538915896L;
	private Integer id;
	private String departmentCode;
	private String departmentName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}
