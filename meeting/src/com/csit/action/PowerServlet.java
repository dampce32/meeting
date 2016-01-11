package com.csit.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.csit.entity.Power;
import com.csit.entity.User;
import com.csit.factory.ServiceFactory;
import com.csit.service.PowerService;
import com.csit.util.JSONUtil;
import com.csit.util.Msg;
import com.csit.util.ServiceResult;

/**
 * @Description:权限Servlet
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class PowerServlet extends HttpServlet {
	private static final long serialVersionUID = -367800897972326370L;
	private static String ACTION_TYPE_QUERY = "query";
	private static String ACTION_TYPE_TREE = "tree";
	private static String ACTION_TYPE_NODE = "node";
	private static String ACTION_TYPE_ADD = "add";
	private static String ACTION_TYPE_DELETE = "delete";
	private static String properties[] = { "powerId", "powerName", "url" };
	private PowerService powerService;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(User.SESSION_LOGIN_USER);
		powerService = ServiceFactory.getInstance().createService(PowerService.class, user);
		try {
			String action = request.getParameter("action");
			if (action == null) {
				String pid = request.getParameter("powerId");
				request.setAttribute("pid",pid);
				request.getRequestDispatcher("/WEB-INF/page/power.jsp").forward(request, response);
			} else if (ACTION_TYPE_QUERY.equals(action)) {
				query(request, response);
			} else if (ACTION_TYPE_TREE.equals(action)) {
				request.getRequestDispatcher("/WEB-INF/page/powerManager.jsp").forward(request, response);
			} else if (ACTION_TYPE_NODE.equals(action)) {
				node(request, response);
			} else if (ACTION_TYPE_ADD.equals(action)) {
				String powerId = request.getParameter("powerId");
				if(powerId == null || "".equals(powerId)){
					add(request,response);
				}else{
					modify(request,response);
				}
			} else if(ACTION_TYPE_DELETE.equals(action)){
				delete(request,response);
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

	private void modify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String powerId = request.getParameter("powerId");
		String url = request.getParameter("url");
		String powerName = request.getParameter("powerName");
		Integer id = null;
		try {
			id = Integer.parseInt(powerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Power power = new Power();
		power.setPowerId(id);
		power.setUrl(url);
		power.setPowerName(powerName);
		result = powerService.update(power);
		response.getWriter().write(result.toString());		
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String powerId = request.getParameter("powerId");
		Integer id = null;
		try {
			id = Integer.parseInt(powerId);
		} catch (Exception e) {
			result.setMessage(Msg.PROGRAM_ERROR);
		}

		result = powerService.delete(id);
		response.getWriter().write(result.toString());
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String url = request.getParameter("url");
		String powerName = request.getParameter("powerName");
		String pid = request.getParameter("pid");
		Integer id = null;
		try {
			id = Integer.parseInt(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Power power = new Power();
		power.setUrl(url);
		power.setPowerName(powerName);
		result = powerService.add(power,id);
		response.getWriter().write(result.toString());
	}

	/**
	 * @Description: 权限树查询
	 * @Create: 2014-2-24 上午11:16:57
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void node(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String roleId = request.getParameter("roleId");
		Integer rid = null;
		try {
			rid = Integer.parseInt(roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = powerService.queryNode(rid);
		response.getWriter().write(json);
	}

	/**
	 * @Description: 查询
	 * @Create: 2014-2-21 下午2:07:07
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void query(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pid = request.getParameter("pid");
		Integer id = null;
		try {
			id = Integer.parseInt(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Power> list = powerService.getList(id);
		String json = JSONUtil.toJson(list, properties);
		response.getWriter().write(json);
	}

}
