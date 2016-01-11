package com.csit.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.csit.dao.UserDao;
import com.csit.entity.User;
import com.csit.util.JdbcUtils;
/**
 * @Description:用户DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class UserDaoImpl implements UserDao {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.UserDao#query(com.csit.entity.User)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public User query(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from T_User where userCode = ? and password = ?";
		String params[] = { user.getUserCode(), user.getPassword() };
		User model = (User) runner.query(sql, params, new BeanHandler(User.class));
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.UserDao#add(com.csit.entity.User)
	 */
	@Override
	public Integer add(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "insert into T_User (userCode,[password],name,gender,phoneNumber,[address],departmentId,mailNumber,fax,qq,note,roleId) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object params[] = { user.getUserCode(), user.getPassword(), user.getName(), user.getGender(), user.getPhoneNumber(), user.getAddress(),
				user.getDepartmentId(), user.getMailNumber(), user.getFax(), user.getQq(), user.getNote(), user.getRoleId() };
		int count = runner.update(sql, params);
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.UserDao#existByUserCode(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean existByUserCode(String userCode) throws SQLException {
		boolean flag = false;
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select count(*) from T_User where userCode = ?";
		String params[] = { userCode };
		Object[] count = (Object[]) runner.query(sql, params, new ArrayHandler());
		if (count != null && count.length > 0) {
			if (Integer.parseInt(count[0].toString()) == 0) {
				flag = true;
			}
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.UserDao#getList(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getList(Integer rows, Integer page) throws SQLException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		page = (rows*(page - 1));
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select top("+rows+") a.*,b.departmentName ");
		buffer.append(" from T_User a left join T_Department b on a.departmentId = b.id ");
		buffer.append("  where( a.userId not in (select top ("+page+") e.userId from T_User e left join T_Department f on e.departmentId = f.id group by e.userId order by e.userId desc))  ");
		buffer.append(" group by a.userId,a.roleId,a.address,a.fax,a.gender,a.mailNumber,a.name,a.note ");
		buffer.append("  ,a.password,a.phoneNumber,a.qq,a.roleId,a.userCode,a.departmentId,b.departmentCode,b.departmentName,b.id");
		buffer.append(" order by a.userId ");
		List<User> list = (List<User>) runner.query(buffer.toString(), new BeanListHandler(User.class));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.UserDao#getTotal()
	 */
	@Override
	public Long getTotal() {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from T_User ";
			Object[] count = (Object[]) runner.query(sql, new ArrayHandler());
			if (count.length > 0) {
				return Long.parseLong(count[0].toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.UserDao#remove(java.lang.Integer)
	 */
	@Override
	public Integer remove(Integer userId) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "delete from T_User where userId = ?";
			String sql2 = "delete from User_Meeting where userId = ?";
			int count = runner.update(sql,userId);
			runner.update(sql2,userId);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.UserDao#queryById(java.lang.Integer)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public User queryById(Integer userId) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from T_User where userId = ? ";
			User model = (User) runner.query(sql,userId, new BeanHandler(User.class));
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.UserDao#update(com.csit.entity.User)
	 */
	@Override
	public Integer update(User user) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update T_User set roleId = ? , [name] = ? , [gender] =? , [phoneNumber]=? , [address]= ?" 
					+" , [departmentId] = ? , [fax]=? , [qq]=? , [note] = ? where [userId] = ?";
			Object params[] = {user.getRoleId(),user.getName(),user.getGender(),user.getPhoneNumber()
					,user.getAddress(),user.getDepartmentId(),user.getFax(),user.getQq(),user.getNote(),user.getUserId()};
			return runner.update(sql,params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.UserDao#resetPassword(com.csit.entity.User)
	 */
	@Override
	public Integer resetPassword(User user) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update T_User set [password] = ? where [userId] = ?";
			Object params[] = {user.getPassword(),user.getUserId()};
			return runner.update(sql,params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
