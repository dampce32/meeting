package com.csit.dao;

import java.sql.SQLException;
import java.util.List;

import com.csit.entity.Power;
/**
 * @Description:权限DAO
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public interface PowerDao {
	/**
	 * @Description: 根据RoleID 返回这个ID所对应的角色所拥有的权限
	 * @Create: 2014-2-20 下午4:35:03
	 * @author lhy
	 * @update logs
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public List<Power> getPowerByRoleId(Integer roleId)throws SQLException;
	/**
	 * @Description: 获得总记录数
	 * @Create: 2014-2-21 下午2:17:15
	 * @author lhy
	 * @update logs
	 * @return
	 */
	public Long getTotal();
	/**
	 * @Description: 查询Tree节点
	 * @Create: 2014-2-24 上午11:23:04
	 * @author lhy
	 * @param rid 
	 * @update logs
	 * @return
	 */
	public List<Power> queryNode(Integer rid);
	/**
	 * @Description: 根据ID查询
	 * @Create: 2014-2-24 下午3:11:53
	 * @author lhy
	 * @update logs
	 * @param pid
	 * @return
	 */
	public Power queryById(Integer pid);
	/**
	 * @Description: 添加
	 * @Create: 2014-2-24 下午3:26:04
	 * @author lhy
	 * @update logs
	 * @param power
	 * @return
	 */
	void add(Power power);
	/**
	 * @Description: 获得子节点
	 * @Create: 2014-2-24 下午5:11:33
	 * @author lhy
	 * @update logs
	 * @param power
	 * @return
	 * @throws SQLException 
	 */
	List<Power> getChild(Power power);
	/**
	 * @Description: 删除
	 * @Create: 2014-3-5 下午2:42:24
	 * @author lhy
	 * @update logs
	 * @param id
	 * @param integer2 
	 * @param integer 
	 * @return
	 */
	public Integer remove(Integer id, Integer integer, Integer integer2);
	/**
	 * @Description: 更新
	 * @Create: 2014-3-5 下午3:08:58
	 * @author lhy
	 * @update logs
	 * @param model
	 * @return
	 */
	public Integer update(Power model);
}
