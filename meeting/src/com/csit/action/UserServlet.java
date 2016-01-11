package com.csit.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.csit.entity.Power;
import com.csit.entity.Role;
import com.csit.entity.User;
import com.csit.factory.ServiceFactory;
import com.csit.service.PowerService;
import com.csit.service.RoleService;
import com.csit.service.UserService;
import com.csit.util.JSONUtil;
import com.csit.util.Msg;
import com.csit.util.ServiceResult;

/**
 * @Description: 用户Servlet
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-12
 * @author lhy
 * @vesion 1.0
 */
public class UserServlet extends HttpServlet {
	private static String ACTION_TYPE_LOGIN = "login";
	private static String ACTION_TYPE_ADD = "add";
	private static String ACTION_TYPE_QUERY = "query";
	private static String Action_TYPE_REMOVE = "remove";
	private static String Action_TYPE_RESET_PASSWORD = "resetPassword";
	private static String Action_TYPE_PERSONAL_UPDATE = "personal";
	private static String Action_TYPE_PERSONAL_PAGE = "personal_page";
	private static String ACTION_TYPE_LOGOUT = "logout";
	private static String properties[] = { "userId", "userCode", "name", "gender", "phoneNumber", "fax", "address", "qq", "departmentName","departmentId", "roleId",
			"note" };
	private UserService userService; 
	private PowerService powerService;
	private RoleService roleService;
	private static final long serialVersionUID = 5141586174428317721L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(User.SESSION_LOGIN_USER);
		userService=ServiceFactory.getInstance().createService(UserService.class,user);
		powerService=ServiceFactory.getInstance().createService(PowerService.class,user);
		roleService=ServiceFactory.getInstance().createService(RoleService.class,user);
		try {
			String action = request.getParameter("action");
			if (action == null) {
				request.getRequestDispatcher("/WEB-INF/page/userManager.jsp").forward(request, response);
			} else if (ACTION_TYPE_LOGIN.equals(action)) {
				login(request, response);
			} else if (ACTION_TYPE_ADD.equals(action)) {
				String userId = request.getParameter("userId");
				if (userId == null || "".equals(userId.trim())) {
					add(request, response);
				} else {
					update(request, response);
				}
			} else if (ACTION_TYPE_QUERY.equals(action)) {
				query(request, response);
			} else if (Action_TYPE_REMOVE.equals(action)) {
				remove(request, response);
			} else if (Action_TYPE_RESET_PASSWORD.equals(action)) {
				resetPassword(request, response);
			} else if (Action_TYPE_PERSONAL_UPDATE.equals(action)) {
				personal(request, response);
			} else if (Action_TYPE_PERSONAL_PAGE.equals(action)) {
				request.getRequestDispatcher("/WEB-INF/page/personal.jsp").forward(request, response);
			} else if (ACTION_TYPE_LOGOUT.equals(action)) {
				logout(request, response);
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

	/**
	 * @Description: 登出
	 * @Create: 2014-2-20 下午4:42:54
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(true);
		request.getSession().removeAttribute(User.SESSION_LOGIN_USER);
		request.getSession().removeAttribute(User.SESSION_LOGIN_POWER);
		ThreadLocal<User> thread = new ThreadLocal<User>();
		thread.remove();
		response.getWriter().write(result.toJSON());
	}

	/**
	 * @Description: 修改个人信息
	 * @Create: 2014-2-20 下午4:43:01
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void personal(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		User user = (User) request.getSession().getAttribute(User.SESSION_LOGIN_USER);
		
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String departmentId = request.getParameter("departmentId");
		String qq = request.getParameter("qq");
		String fax = request.getParameter("fax");
		String address = request.getParameter("address");
		String mailNumber = request.getParameter("mailNumber");
		String phoneNumber = request.getParameter("phoneNumber");
		String note = request.getParameter("note");
		Integer dId = null;
		try {
			dId = Integer.parseInt(departmentId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
		}
		user.setAddress(address);
		user.setDepartmentId(dId);
		user.setFax(fax);
		user.setGender(gender);
		user.setMailNumber(mailNumber);
		user.setName(name);
		user.setNote(note);
		user.setQq(qq);
		user.setPhoneNumber(phoneNumber);
		result = userService.personal(user);
		response.getWriter().write(result.toString());
	}

	/**
	 * @Description: 重置密码。重置以后的密码为msg.properties所配置的默认密码
	 * @Create: 2014-2-15 上午11:30:20
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String userId = request.getParameter("userId");
		Integer id = null;
		try {
			id = Integer.parseInt(userId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
			response.getWriter().write(result.toString());
			return;
		}
		result = userService.resetPasswrod(id);
		response.getWriter().write(result.toString());
	}

	/**
	 * @Description: 修改User对象 并返回修改结果 result
	 * @Create: 2014-2-15 下午3:47:03
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String name = request.getParameter("name");
		String userId = request.getParameter("userId");
		String gender = request.getParameter("gender");
		String departmentId = request.getParameter("departmentId");
		String roleId = request.getParameter("roleId");
		String qq = request.getParameter("qq");
		String fax = request.getParameter("fax");
		String address = request.getParameter("address");
		String mailNumber = request.getParameter("mailNumber");
		String phoneNumber = request.getParameter("phoneNumber");
		String note = request.getParameter("note");
		Integer dId = null;
		Integer rId = null;
		Integer id = null;
		try {
			dId = Integer.parseInt(departmentId);
			rId = Integer.parseInt(roleId);
			id = Integer.parseInt(userId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
		}
		User user = new User();
		user.setUserId(id);
		user.setAddress(address);
		user.setDepartmentId(dId);
		user.setFax(fax);
		user.setGender(gender);
		user.setMailNumber(mailNumber);
		user.setName(name);
		user.setNote(note);
		user.setQq(qq);
		user.setPhoneNumber(phoneNumber);
		user.setRoleId(rId);
		result = userService.update(user);
		response.getWriter().write(result.toString());
	}

	/**
	 * @Description: 登录
	 * @Create: 2014-2-14 上午10:13:25
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceResult result = new ServiceResult(false);
		String userCode = request.getParameter("userCode");
		String password = request.getParameter("password");
		String loginCaptcha = request.getParameter("loginCaptcha");
		String old = (String) request.getSession().getAttribute("loginCaptcha");
		if(loginCaptcha==null || !loginCaptcha.equals(old)){
			result.setMessage("验证码错误");
			response.getWriter().write(result.toString());
			return;
		}
		User user = new User();
		user.setUserCode(userCode);
		user.setPassword(password);
		result = userService.login(user);
		if (result.isSuccess()) {
			User model = (User) result.getObject();
			if (model.getRoleId() == null) {
				result.setMessage(Msg.PROGRAM_ERROR);
				response.getWriter().write(result.toString());
				return;
			}
			Role role = roleService.queryById(model.getRoleId());
			request.getSession().setAttribute(Role.SESSION_LOGIN_ROLE, role);
			List<Power> list = powerService.getPowerByRoleId(model.getRoleId());
			request.getSession().setAttribute(User.SESSION_LOGIN_USER, model);
			request.getSession().setAttribute(User.SESSION_LOGIN_POWER, list);
			ThreadLocal<User> local = new ThreadLocal<User>();
			local.set(model);
			response.getWriter().write(result.toString());
		} else {
			response.getWriter().write(result.toString());
		}
	}

	/**
	 * @Description: 新增用户
	 * @Create: 2014-2-20 下午4:43:22
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceResult result = new ServiceResult(false);
		String userCode = request.getParameter("userCode");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String departmentId = request.getParameter("departmentId");
		String roleId = request.getParameter("roleId");
		String qq = request.getParameter("qq");
		String fax = request.getParameter("fax");
		String address = request.getParameter("address");
		String mailNumber = request.getParameter("mailNumber");
		String phoneNumber = request.getParameter("phoneNumber");
		String note = request.getParameter("note");
		Integer dId = null;
		Integer rId = null;
		try {
			dId = Integer.parseInt(departmentId);
			rId = Integer.parseInt(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
		}
		User user = new User();
		user.setAddress(address);
		user.setDepartmentId(dId);
		user.setFax(fax);
		user.setGender(gender);
		user.setMailNumber(mailNumber);
		user.setName(name);
		user.setNote(note);
		user.setQq(qq);
		user.setPhoneNumber(phoneNumber);
		user.setRoleId(rId);
		user.setUserCode(userCode);

		result = userService.add(user);
		response.getWriter().write(result.toString());
	}

	/**
	 * @Description: 查询用户密码是否存在
	 * @Create: 2014-2-20 下午4:43:32
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		List<User> list = userService.getList(r, p);
		Long total = userService.getTotal();
		String json = JSONUtil.toJson(list, properties, total);
		response.getWriter().write(json);
	}

	/**
	 * @Description: 删除用户
	 * @Create: 2014-2-20 下午4:43:55
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceResult result = new ServiceResult(false);
		String userId = request.getParameter("userId");
		Integer id = null;
		try {
			id = Integer.parseInt(userId);
		} catch (Exception e) {
			result.setMessage(Msg.PROGRAM_ERROR);
		}
		result = userService.remove(id);
		response.getWriter().write(result.toString());
	}
}
