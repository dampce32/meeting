package com.csit.entity;

import java.util.Date;
/**
 * @Description:会议
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class Meeting {
	/**
	 * ID
	 */
	private Integer meetingId;
	/**
	 * 会议名称
	 */
	private String name;
	/**
	 * 报名开始时间
	 */
	private Date registerBegin;
	/**
	 * 报名结束时间
	 */
	private Date registerEnd;
	/**
	 * 会议开始时间
	 */
	private Date beginDate;
	/**
	 * 会议结束时间
	 */
	private Date endDate; 
	/**
	 * 会务费
	 */
	private Double money;
	/**
	 * 开会地址
	 */
	private String address;
	/**
	 * 主办单位
	 */
	private String host;
	/**
	 * 承办单位
	 */
	private String organizer;
	/**
	 * 协办单位
	 */
	private String co_organizer;
	/**
	 * 已经报名人数
	 */
	private Integer peopleNumber;
	/**
	 * 会务费支付方式
	 */
	private String pay;
	public Integer getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getRegisterBegin() {
		return registerBegin;
	}
	public void setRegisterBegin(Date registerBegin) {
		this.registerBegin = registerBegin;
	}
	public Date getRegisterEnd() {
		return registerEnd;
	}
	public void setRegisterEnd(Date registerEnd) {
		this.registerEnd = registerEnd;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public String getCo_organizer() {
		return co_organizer;
	}
	public void setCo_organizer(String co_organizer) {
		this.co_organizer = co_organizer;
	}
	public Integer getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
}
