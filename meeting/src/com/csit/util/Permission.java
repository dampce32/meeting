package com.csit.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @Description:注解  应当加在Service层需要权限拦截的方法上
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
	public static String ADMIN = "ADMIN";
	String value();
}
