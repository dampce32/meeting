package com.csit.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.csit.entity.Department;
import com.csit.entity.User;
import com.csit.factory.ServiceFactory;
import com.csit.service.DepartmentService;
import com.csit.util.JSONUtil;
import com.csit.util.Msg;
import com.csit.util.ServiceResult;
/**
 * @Description:部门servlet
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-3-6
 * @author lhy
 * @vesion 1.0
 */
public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 6987928855836355811L;
	private static String ACTION_TYPE_ADD = "add";
	private static String ACTION_TYPE_QUERY = "query";
	private static String Action_TYPE_REMOVE = "remove";
	private static String ACTION_TYPE_COMBOBOX = "combobox";
	private static String properties[] = { "id", "departmentCode", "departmentName"};
	private DepartmentService departmentService;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(User.SESSION_LOGIN_USER);
		departmentService=ServiceFactory.getInstance().createService(DepartmentService.class,user);
		try {
			String action = request.getParameter("action");
			if (action == null) {
				request.getRequestDispatcher("/WEB-INF/page/department.jsp").forward(request, response);
			} else if (ACTION_TYPE_ADD.equals(action)) {
				String id = request.getParameter("id");
				if (id == null || "".equals(id.trim())) {
					add(request, response);
				} else {
					update(request, response);
				}
			} else if (ACTION_TYPE_QUERY.equals(action)) {
				query(request, response);
			} else if (Action_TYPE_REMOVE.equals(action)) {
				remove(request, response);
			} else if(ACTION_TYPE_COMBOBOX.equals(action)){
				combobox(request,response);
			}
		} catch (Exception e) {
			if (e instanceof java.lang.SecurityException) {
				request.setAttribute("message", Msg.NO_POWER);
			} else {
				e.printStackTrace();
				JSONObject object = new JSONObject();
				object.put("message", Msg.PROGRAM_ERROR);
				response.getWriter().write(object.toString());
			}
		}
	}

	private void combobox(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Department> departments = departmentService.queryAll();
		JSONArray array = new JSONArray();
		for(Department department : departments){
			array.add(department);
		}
		response.getWriter().write(array.toString());
	}

	private void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String id = request.getParameter("id");
		Integer did = null;
		try {
			did = Integer.parseInt(id);
		} catch (Exception e) {
			result.setMessage(Msg.PROGRAM_ERROR);
		}
		result = departmentService.remove(did);
		response.getWriter().write(result.toString());
	}

	private void query(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		Integer r = null;
		Integer p = null;
		try {
			r = Integer.parseInt(rows);
			p = Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Department> list = departmentService.getList(r, p);
		Long total = departmentService.getTotal();
		String json = JSONUtil.toJson(list, properties, total);
		response.getWriter().write(json);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String id = request.getParameter("id");
		String departmentCode = request.getParameter("departmentCode");
		String departmentName = request.getParameter("departmentName");
		
		Integer dId = null;
		try {
			dId = Integer.parseInt(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
		}
		Department department = new Department();
		department.setId(dId);
		department.setDepartmentCode(departmentCode);
		department.setDepartmentName(departmentName);
		result = departmentService.update(department);
		response.getWriter().write(result.toString());
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String departmentCode = request.getParameter("departmentCode");
		String departmentName = request.getParameter("departmentName");
		Department department = new Department();
		department.setDepartmentCode(departmentCode);
		department.setDepartmentName(departmentName);

		result = departmentService.add(department);
		response.getWriter().write(result.toString());
	}

}
