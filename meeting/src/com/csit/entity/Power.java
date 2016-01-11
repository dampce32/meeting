package com.csit.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:权限
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-13
 * @author lhy
 * @vesion 1.0
 */
public class Power implements Serializable{
	private static final long serialVersionUID = -8982845282382108233L;
	/**
	 * ID
	 */
	private Integer powerId;
	/**
	 * 权限名称
	 */
	private String powerName;
	/**
	 * 左值
	 */
	private Integer lft;
	/**
	 * 右值
	 */
	private Integer rgt;
	/**
	 * 所在层次（深度）
	 */
	private Integer deepth;
	/**
	 * URL
	 */
	private String url;
	/**
	 * 父节点
	 */
	private List<Power> parent;
	private Integer roleId;
	public Integer getPowerId() {
		return powerId;
	}
	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}
	public String getPowerName() {
		return powerName;
	}
	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}
	public Integer getLft() {
		return lft;
	}
	public void setLft(Integer lft) {
		this.lft = lft;
	}
	public Integer getRgt() {
		return rgt;
	}
	public void setRgt(Integer rgt) {
		this.rgt = rgt;
	}
	public Integer getDeepth() {
		return deepth;
	}
	public void setDeepth(Integer deepth) {
		this.deepth = deepth;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Power> getParent() {
		return parent;
	}
	public void setParent(List<Power> parent) {
		this.parent = parent;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
