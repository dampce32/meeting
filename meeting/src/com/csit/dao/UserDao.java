package com.csit.dao;

import java.sql.SQLException;
import java.util.List;

import com.csit.entity.User;
/**
 * @Description:用户DAO
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public interface UserDao {
	/**
	 * @Description: 查询User 对象的 用户密码是否在数据库中存在
	 * @Create: 2014-2-20 下午4:37:11
	 * @author lhy
	 * @update logs
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User query(User user)throws SQLException;
	/**
	 * @Description: 添加
	 * @Create: 2014-2-20 下午4:37:46
	 * @author lhy
	 * @update logs
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public Integer add(User user) throws SQLException;
	/**
	 * @Description: 查询用户名是否已经存在
	 * @Create: 2014-2-20 下午4:37:53
	 * @author lhy
	 * @update logs
	 * @param userCode
	 * @return
	 * @throws SQLException
	 */
	public boolean existByUserCode(String userCode)throws SQLException;
	/**
	 * @Description: 分页查询所有用户
	 * @Create: 2014-2-20 下午4:38:04
	 * @author lhy
	 * @update logs
	 * @param rows
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public List<User> getList(Integer rows, Integer page)throws SQLException;
	/**
	 * @Description: 得到用户的总记录数
	 * @Create: 2014-2-20 下午4:38:20
	 * @author lhy
	 * @update logs
	 * @return
	 */
	public Long getTotal();
	/**
	 * @Description: 删除
	 * @Create: 2014-2-20 下午4:38:33
	 * @author lhy
	 * @update logs
	 * @param userId
	 * @return
	 */
	public Integer remove(Integer userId);
	/**
	 * @Description: 根据ID查询用户
	 * @Create: 2014-2-20 下午4:38:40
	 * @author lhy
	 * @update logs
	 * @param integer
	 * @return
	 */
	public User queryById(Integer integer);
	/**
	 * @Description: 更新
	 * @Create: 2014-2-20 下午4:38:51
	 * @author lhy
	 * @update logs
	 * @param user
	 * @return
	 */
	public Integer update(User user);
	/**
	 * @Description: 重置密码
	 * @Create: 2014-2-20 下午4:38:59
	 * @author lhy
	 * @update logs
	 * @param user
	 * @return
	 */
	public Integer resetPassword(User user);
}
