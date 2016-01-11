package com.csit.service;

import java.util.List;

import com.csit.entity.Department;
import com.csit.util.ServiceResult;

public interface DepartmentService {

	ServiceResult update(Department department);

	ServiceResult add(Department department);

	List<Department> getList(Integer r, Integer p);

	Long getTotal();

	ServiceResult remove(Integer id);
	
	List<Department> queryAll();

}
