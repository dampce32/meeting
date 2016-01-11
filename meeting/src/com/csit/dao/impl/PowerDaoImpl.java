package com.csit.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.csit.dao.PowerDao;
import com.csit.entity.Power;
import com.csit.util.JdbcUtils;

/**
 * @Description: 权限DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class PowerDaoImpl implements PowerDao {
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PowerDao#getPowerByRoleId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Power> getPowerByRoleId(Integer roleId) throws SQLException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select child.url AS url,	child.powerId AS powerId,	child.powerName AS powerName,	child.lft AS lft,	child.rgt AS rgt,"
				+ " count(child.powerName) deepth from T_Power parent,T_Power child where child.lft>=parent.lft and child.rgt<=parent.rgt and parent.roleId = "+ roleId +" and child.roleId = "+roleId
				+ " group by child.powerId , child.url , child.powerName , child.lft , child.rgt order by child.lft";
		
		List<Power> list = (List<Power>) runner.query(sql, new BeanListHandler(Power.class));
		return list;
	}
	
	/*
	 * (non-Javadoc)   
	 * @see com.csit.dao.PowerDao#getList(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Power> getChild(Power power) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql ="select * from T_Power where lft>? and rgt<? order by lft";
		Object [] params = {power.getLft(),power.getRgt()};
		List<Power> list = null;
		try {
			list = (List<Power>) runner.query(sql,params, new BeanListHandler(Power.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public Long getTotal() {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from T_Power  ";
			Object[] count = (Object[]) runner.query(sql, new ArrayHandler());
			if (count.length > 0) {
				return Long.parseLong(count[0].toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Power> queryNode(Integer rid) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select child.url AS url,	child.powerId AS powerId,	child.powerName AS powerName,	child.lft AS lft,	child.rgt AS rgt,"
				+ " count(child.powerName) deepth from T_Power parent,T_Power child where child.lft>=parent.lft and child.rgt<=parent.rgt and child.roleId in (3,"+rid +") and parent.roleId in (3,"+rid +")"
				+ " group by child.powerId , child.url , child.powerName , child.lft , child.rgt order by child.lft";
		List<Power> list = null;
		try {
			list = (List<Power>) runner.query(sql, new BeanListHandler(Power.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public void add(Power power){
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "insert into T_Power (roleId,powerName,url,lft,rgt) values (?,?,?,?,?)";
		Object [] params = {power.getRoleId(),power.getPowerName(),power.getUrl(),power.getLft(),power.getRgt()};
		
		String sql1 = "update T_Power set lft=lft+2 where lft>=?";
		String sql2 = "update T_Power set rgt=rgt+2 where rgt>=?";
		try {
			runner.update(sql1,power.getLft());
			runner.update(sql2,power.getRgt()-1);
			runner.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public Power queryById(Integer pid) {
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from T_power where powerId= ?";
			Power power = (Power) runner.query(sql, pid, new BeanHandler(Power.class));
			sql = "select * from T_power where lft<? and rgt>? order by lft";
			
			Object params[] = {power.getLft(),power.getRgt()};
			List<Power> list = (List<Power>) runner.query(sql, params, new BeanListHandler(Power.class));
			power.setParent(list);
			return power;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Integer remove(Integer id, Integer lft, Integer rgt) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "delete from T_Power where meetingId = ?";
			Integer value = rgt - lft +1; 
			String sql2 = "update T_Power set lft = lft - ? , rgt = rgt - ?";
			int count = runner.update(sql, id);
			Object [] params = {value,value};
			runner.update(sql2, params);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Integer update(Power model) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update T_Power set powerName = ? , url = ? where powerId = ?";
			Object params[] = {  model.getPowerName(),model.getUrl(),model.getPowerId()};
			return runner.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
