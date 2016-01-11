package com.csit.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
/**
 * @Description:系统提示信息
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class Msg {
	/**
	 * 部门数据被关联
	 */
	public static String DEPARTMENT_USED="DEPARTMENT_USED";
	/**
	 * 该部门编号已经存在
	 */
	public static String DEPARTMENT_EXIST = "DEPARTMENT_EXIST";
	/**
	 * 请填写部门名称
	 */
	public static String PLEASE_INPUT_DEPARTMENT_NAME = "PLEASE_INPUT_DEPARTMENT_NAME";
	/**
	 * 请填写部门编号
	 */
	public static String PLEASE_INPUT_DEPARTMENTCODE = "PLEASE_INPUT_DEPARTMENTCODE";
	/**
	 * 请填写用户名
	 */
	public static String PLEASE_INPUT_USERCODE ="PLEASE_INPUT_USERCODE";
	/**
	 * 请填写密码
	 */
	public static String PLEASE_INPUT_PASSWORD ="PLEASE_INPUT_PASSWORD";
	/**
	 * 默认密码
	 */
	public static String DEFAULT_PASSWORD="DEFAULT_PASSWORD";
	/**
	 * 程序异常
	 */
	public static String PROGRAM_ERROR="PROGRAM_ERROR";
	/**
	 * 没有权限
	 */
	public static String NO_POWER = "NO_POWER";
	/**
	 * 请填写会议名称
	 */
	public static String PLEASE_INPUT_MEETING_NAME ="PLEASE_INPUT_MEETING_NAME";
	/**
	 * 请填写会议报名开始时间
	 */
	public static String PLEASE_SELECT_MEETING_REGISTER_BEGIN ="PLEASE_SELECT_MEETING_REGISTER_BEGIN";
	/**
	 * 请填写会议报名结束时间
	 */
	public static String PLEASE_SELECT_MEETING_REGISTER_END = "PLEASE_SELECT_MEETING_REGISTER_END";
	/**
	 * 会议报名时间不能超过结束时间
	 */
	public static String REGISTER_BEGIN_CANNOT_EXCESS = "REGISTER_BEGIN_CANNOT_EXCESS";
	/**
	 * 请填写主办单位
	 */
	public static String PLEASE_INPUT_HOST = "PLEASE_INPUT_HOST";
	/**
	 * 请填写开会地址
	 */
	public static String PLEASE_INPUT_MEETING_ADDRESS = "PLEASE_INPUT_MEETING_ADDRESS";
	/**
	 * 请填写会务费
	 */
	public static String PLEASE_INPUT_MONEY = "PLEASE_INPUT_MONEY";
	/**
	 * 报名结束时间不能超过会议开始时间
	 */
	public static String REGISTER_END_CANNOT_EXCESS = "REGISTER_END_CANNOT_EXCESS";
	/**
	 * 会议开始时间不能超过结束时间
	 */
	public static String MEETING_BEGIN_CANNOT_EXCESS = "MEETING_BEGIN_CANNOT_EXCESS";
	/**
	 * 请选择会议结束时间
	 */
	public static String PLEASE_SELECT_MEETING_END = "PLEASE_SELECT_MEETING_END";
	/**
	 * 请选择会议开始时间
	 */
	public static String PLEASE_SELECT_MEETING_BEGIN = "PLEASE_SELECT_MEETING_BEGIN";
	/**
	 * 添加成功
	 */
	public static String ADD_SUCCESS = "ADD_SUCCESS";
	/**
	 * 会议删除失败
	 */
	public static String DELETE_MEETING_ERROR = "DELETE_MEETING_ERROR";
	/**
	 * 删除成功
	 */
	public static String DELETE_SUCCESS = "DELETE_SUCCESS";
	/**
	 * 更新成功
	 */
	public static String UPDATE_SUCCESS = "UPDATE_SUCCESS";
	/**
	 * 会议报名失败
	 */
	public static String REGISTER_MEETING_ERROR = "REGISTER_MEETING_ERROR";
	/**
	 * 会议已经报名
	 */
	public static String MEETING_ALREADY_REGISTER = "MEETING_ALREADY_REGISTER";
	/**
	 * 会议报名成功
	 */
	public static String REGISTER_MEETING_SUCCESS = "REGISTER_MEETING_SUCCESS";
	/**
	 * 取消参会失败
	 */
	public static String MEETING_CANNOT_CANCEL = "MEETING_CANNOT_CANCEL";
	/**
	 * 取消参会成功
	 */
	public static String MEETING_CANCEL_SUCCESS = "MEETING_CANCEL_SUCCESS";
	/**
	 * 请填写URL
	 */
	public static String PLEASE_INPUT_URL = "PLEASE_INPUT_URL";
	/**
	 * 请填写权限名称
	 */
	public static String PLEASE_INPUT_POWER_NAME = "PLEASE_INPUT_POWER_NAME";
	/**
	 * 登录失败
	 */
	public static String LOGIN_ERROR = "LOGIN_ERROR";
	/**
	 * 请输入用户姓名
	 */
	public static String PLEASE_INPUT_USER_NAME = "PLEASE_INPUT_USER_NAME";
	/**
	 * 请选择性别
	 */
	public static String PLEASE_SELECT_GENDER = "PLEASE_SELECT_GENDER";
	/**
	 * 请选择部门
	 */
	public static String PLEASE_SELECT_DEPARTMENT = "PLEASE_SELECT_DEPARTMENT";
	/**
	 * 请选择角色
	 */
	public static String PLEASE_SELECT_ROLE = "PLEASE_SELECT_ROLE";
	/**
	 * 请填写电话
	 */
	public static String PLEASE_INPUT_PHONE = "PLEASE_INPUT_PHONE";
	/**
	 * 请选择要操作的数据
	 */
	public static String PLEASE_SELECT_DATA = "PLEASE_SELECT_DATA";
	/**
	 * 操作成功
	 */
	public static String OPERATION_SUCCESS = "OPERATION_SUCCESS";
	/**
	 * 用户已经存在
	 */
	public static String USER_EXIST = "USER_EXIST";
	
	
	private static List<String> list = new ArrayList<String>();
	static{
		list.add(PLEASE_INPUT_USERCODE);
		list.add(PLEASE_INPUT_PASSWORD);
		list.add(DEFAULT_PASSWORD);
		list.add(PROGRAM_ERROR);
		list.add(NO_POWER);
		list.add(PLEASE_INPUT_MEETING_NAME);
		list.add(PLEASE_SELECT_MEETING_REGISTER_BEGIN);
		list.add(PLEASE_SELECT_MEETING_REGISTER_END);
		list.add(REGISTER_BEGIN_CANNOT_EXCESS);
		list.add(PLEASE_INPUT_HOST);
		list.add(PLEASE_INPUT_MONEY);
		list.add(PLEASE_INPUT_MEETING_ADDRESS);
		list.add(REGISTER_END_CANNOT_EXCESS);
		list.add(MEETING_BEGIN_CANNOT_EXCESS);
		list.add(PLEASE_SELECT_MEETING_END);
		list.add(PLEASE_SELECT_MEETING_BEGIN);
		list.add(ADD_SUCCESS);
		list.add(DELETE_MEETING_ERROR);
		list.add(DELETE_SUCCESS);
		list.add(UPDATE_SUCCESS);
		list.add(REGISTER_MEETING_ERROR);
		list.add(MEETING_ALREADY_REGISTER);
		list.add(REGISTER_MEETING_SUCCESS);
		list.add(MEETING_CANNOT_CANCEL);
		list.add(MEETING_CANCEL_SUCCESS);
		list.add(PLEASE_INPUT_URL);
		list.add(PLEASE_INPUT_POWER_NAME);
		list.add(LOGIN_ERROR);
		list.add(PLEASE_INPUT_USER_NAME);
		list.add(PLEASE_SELECT_GENDER);
		list.add(PLEASE_SELECT_DEPARTMENT);
		list.add(PLEASE_SELECT_ROLE);
		list.add(PLEASE_SELECT_DATA);
		list.add(OPERATION_SUCCESS);
		list.add(USER_EXIST);
		list.add(PLEASE_INPUT_DEPARTMENTCODE);
		list.add(PLEASE_INPUT_DEPARTMENT_NAME);
		list.add(DEPARTMENT_EXIST);
		list.add(DEPARTMENT_USED);
		String path = Msg.class.getClassLoader().getResource("msg.properties").getPath();
		readProperties(path);
	}
	// 读取properties的全部信息
	@SuppressWarnings("rawtypes")
	public static void readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				for(String temp : list){
					if(key!=null &&key.equals(temp)){
						Field field = Class.forName("com.csit.util.Msg").getField(temp);
						field.set(null, Property);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(Msg.PROGRAM_ERROR);
	}
}
