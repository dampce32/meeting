package com.csit.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
/**
 * JDBC工具类
 * @Description:
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-12
 * @author lhy
 * @vesion 1.0
 */
@SuppressWarnings("static-access")
public class JdbcUtils {
	/*
	 * 数据源
	 */
	private static DataSource ds;
	/*
	 * 线程上的数据源 
	 */
	private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	static{
		try{
			Properties prop = new Properties();
			InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			prop.load(in);
			BasicDataSourceFactory factory = new BasicDataSourceFactory();
			ds = factory.createDataSource(prop);
		}catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static DataSource getDataSource(){
		return ds;
	}
	
	public static Connection getConnection() throws SQLException{
		try{
			Connection conn = local.get();
			if(conn==null){ 
				conn = ds.getConnection();
				local.set(conn);
			}
			return conn;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void startTransaction(){
		try{
			Connection conn = local.get();
			if(conn==null){  
				conn = ds.getConnection();
				local.set(conn);
			}
			conn.setAutoCommit(false);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void commitTransaction(){
		try{
			Connection conn = local.get();
			if(conn!=null){
				conn.commit();
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void closeConnection(){
		try{
			Connection conn = local.get();
			if(conn!=null){
				conn.close();
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			local.remove();  
		}
	}
}
