package com.csit.service;

import java.util.List;

import com.csit.entity.User;
import com.csit.util.ServiceResult;
/**
 * @Description: 用户Service
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public interface UserService {

	/**
	 * @Description: 添加用户 返回添加结果
	 * @Create: 2014-2-20 下午4:25:06
	 * @author lhy
	 * @update logs
	 * @param user
	 * @return
	 */
	public ServiceResult add(User user);
	/**
	 * @Description: 查询所有用户信息，返回User对象集合
	 * @Create: 2014-2-14 下午2:31:58
	 * @author lhy
	 * @param page 每个页面多少行
	 * @param rows 当前页数
	 * @update logs
	 * @return
	 */
	public List<User> getList(Integer rows, Integer page);
	/**
	 * @Description: 返回 T_User表 数据总条目 数量
	 * @Create: 2014-2-14 下午3:18:23
	 * @author lhy
	 * @update logs
	 * @return
	 */
	public Long getTotal();
	/**
	 * @Description: 根据userId 删除用户对象
	 * @Create: 2014-2-20 下午4:25:23
	 * @author lhy
	 * @update logs
	 * @param userId
	 * @return
	 */
	public ServiceResult remove(Integer userId);
	/**
	 * @Description: 更新目标User对象
	 * @Create: 2014-2-15 上午10:18:58
	 * @author lhy
	 * @update logs
	 * @param user
	 * @return 
	 */
	public ServiceResult update(User user);
	/**
	 * @Description: 重置密码。重置以后的密码为msg.properties所配置的默认密码
	 * @Create: 2014-2-15 上午11:33:19
	 * @author lhy
	 * @update logs
	 * @param userId
	 * @return 
	 */
	public ServiceResult resetPasswrod(Integer userId);

	/**
	 * @Description: 参数user对象 必须设置赋有userCode和 password 值，
	 * 根据用户提供的userCode 和 password 查询数据库 
	 *  前台页面根据返回值进行后续操作
	 * @Create: 2014-2-15 下午3:53:27
	 * @author lhy
	 * @update logs
	 * @param user
	 * @return
	 */
	public ServiceResult login(User user);
	/**
	 * @Description: 修改个人信息
	 * @Create: 2014-2-20 下午4:25:46
	 * @author lhy
	 * @update logs
	 * @param user
	 * @return
	 */
	public ServiceResult personal(User user);


}
