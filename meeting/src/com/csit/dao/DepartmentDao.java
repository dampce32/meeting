package com.csit.dao;

import java.sql.SQLException;
import java.util.List;

import com.csit.entity.Department;

public interface DepartmentDao {

	Integer add(Department department);

	boolean existByCode(String departmentCode);

	List<Department> getList(Integer rows, Integer page);

	Long getTotal();

	Integer remove(Integer id) throws SQLException;

	Department queryById(Integer id);

	Integer update(Department model);

	List<Department> queryAll();

}
