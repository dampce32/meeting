package com.csit.service;

import java.util.List;

import com.csit.entity.Power;
import com.csit.util.ServiceResult;
/**
 * @Description: 权限Service
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public interface PowerService {
	/**
	 * @Description: 根据角色ID获得权限
	 * @Create: 2014-2-21 下午2:09:07
	 * @author lhy
	 * @update logs
	 * @param roleId
	 * @return
	 */
	List<Power> getPowerByRoleId(Integer roleId);
	/**
	 * @Description: 分页查询
	 * @Create: 2014-2-21 下午2:09:24
	 * @author lhy
	 * @update logs
	 * @param r
	 * @return
	 */
	List<Power> getList(Integer r);
	/**
	 * @Description: 获得总记录数
	 * @Create: 2014-2-21 下午2:10:07
	 * @author lhy
	 * @update logs
	 * @return
	 */
	Long getTotal();
	/**
	 * @Description: 权限树查询
	 * @Create: 2014-2-24 上午11:21:05
	 * @author lhy
	 * @param rid 
	 * @update logs
	 * @return
	 */
	String queryNode(Integer rid);
	/**
	 * @Description: 添加
	 * @Create: 2014-2-24 下午2:51:02
	 * @author lhy
	 * @update logs
	 * @param power
	 * @param id 
	 * @return
	 */
	ServiceResult add(Power power, Integer id);
	/**
	 * @Description: 根据ID查询
	 * @Create: 2014-2-24 下午3:10:39
	 * @author lhy
	 * @update logs
	 * @param pid
	 * @return
	 */
	Power queryById(Integer pid);
	/**
	 * @Description: 删除
	 * @Create: 2014-3-5 下午2:41:04
	 * @author lhy
	 * @update logs
	 * @param id
	 * @return
	 */
	ServiceResult delete(Integer id);
	/**
	 * @Description: 更新
	 * @Create: 2014-3-5 下午3:04:21
	 * @author lhy
	 * @update logs
	 * @param power
	 * @return
	 */
	ServiceResult update(Power power);
}
