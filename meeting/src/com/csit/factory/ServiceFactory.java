package com.csit.factory;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

import com.csit.entity.User;
import com.csit.util.Permission;
/**
 * @Description:Service工厂
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class ServiceFactory {
	private static Properties prop = new Properties();
	private ServiceFactory(){
		try{
			InputStream in = DaoFactory.class.getClassLoader().getResourceAsStream("com/csit/factory/service.properties");
			prop.load(in);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	};
	private static final ServiceFactory instance = new ServiceFactory();
	public static ServiceFactory getInstance(){
		return instance;
	}
	/**
	 * @Description: 代理拦截 返回代理对象
	 * @Create: 2014-2-20 下午5:08:20
	 * @author lhy
	 * @update logs
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T createService(Class<T> interfaceClass,final User user){
		try{
			String key = interfaceClass.getSimpleName();
			String className = prop.getProperty(key);
			final T t = (T) Class.forName(className).newInstance();
			return (T) Proxy.newProxyInstance(ServiceFactory.class.getClassLoader(), t.getClass().getInterfaces(), new InvocationHandler(){
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					String methodName = method.getName();
					Method realMethod = t.getClass().getMethod(methodName, method.getParameterTypes());
					Permission permission = realMethod.getAnnotation(Permission.class);
					if(permission==null){
						return method.invoke(t, args);
					}
					String value = permission.value();
					if("ADMIN".equals(value)){
						if(user == null){
							throw new SecurityException("没有登录");
						}
						Integer roleId = user.getRoleId();
						if(roleId == 1){
							return method.invoke(t, args);
						}
					}
					throw new SecurityException("没有权限");
				}
			});
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
