package com.csit.factory;

import java.io.InputStream;
import java.util.Properties;
/**
 * @Description:DAO工厂
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class DaoFactory {
	
	private static Properties prop = new Properties();
	private DaoFactory(){
		try{
			InputStream in = DaoFactory.class.getClassLoader().getResourceAsStream("com/csit/factory/dao.properties");
			prop.load(in);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	};
	private static final DaoFactory instance = new DaoFactory();
	public static DaoFactory getInstance(){
		return instance;
	}
	/**
	 * @Description: 传入DAO接口class返回 DAO实现类
	 * @Create: 2014-2-20 下午4:31:43
	 * @author lhy
	 * @update logs
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T createDao(Class<T> interfaceClass){
		try{
			String key = interfaceClass.getSimpleName();
			String className = prop.getProperty(key);
			return (T) Class.forName(className).newInstance();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
