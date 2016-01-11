package com.csit.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.csit.dao.DepartmentDao;
import com.csit.entity.Department;
import com.csit.util.JdbcUtils;

public class DepartmentDaoImpl implements DepartmentDao{

	@Override
	public Integer add(Department department) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "insert into T_Department ( departmentCode,departmentName )values(?,?)";
		Object params[] = { department.getDepartmentCode(),department.getDepartmentName()};
		int count = 0;
		try {
			count = runner.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return count;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean existByCode(String departmentCode) {
		boolean flag = false;
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select count(*) from T_Department where departmentCode = ?";
		String params[] = { departmentCode };
		Object[] count = null;
		try {
			count = (Object[]) runner.query(sql, params, new ArrayHandler());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if (count != null && count.length > 0) {
			if (Integer.parseInt(count[0].toString()) == 0) {
				flag = true;
			}
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getList(Integer rows, Integer page) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		page = (rows*(page - 1));
		String sql = "select top("+rows+")* from T_Department where(id not in( select top("+page+") id from T_Department order by departmentCode))order by departmentCode   ";
		List<Department> list = null;
		try {
			list = (List<Department>) runner.query(sql, new BeanListHandler(Department.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	@Override
	public Long getTotal() {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from T_Department ";
			Object[] count = (Object[]) runner.query(sql, new ArrayHandler());
			if (count.length > 0) {
				return Long.parseLong(count[0].toString());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return 0L;
	}

	@Override
	public Integer remove(Integer id) throws SQLException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql= "delete from T_Department where id = ?";
		int count = runner.update(sql,id);
		return count;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Department queryById(Integer id) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from T_Department where id = ? ";
			Department model = (Department) runner.query(sql,id, new BeanHandler(Department.class));
			return model;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Integer update(Department model) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update T_Department set departmentName = ? , departmentCode = ? where id = ?";
			Object params[] = {model.getDepartmentName(),model.getDepartmentCode(),model.getId()};
			return runner.update(sql,params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> queryAll() {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from T_Department ";
			List<Department> list = (List<Department>) runner.query(sql, new BeanListHandler(Department.class));
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
