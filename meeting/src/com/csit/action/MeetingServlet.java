package com.csit.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.csit.entity.Meeting;
import com.csit.entity.ReportConfig;
import com.csit.entity.User;
import com.csit.factory.ServiceFactory;
import com.csit.service.MeetingService;
import com.csit.util.GenXmlData;
import com.csit.util.JSONUtil;
import com.csit.util.Msg;
import com.csit.util.ServiceResult;

/**
 * @Description: 会议Servlet
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class MeetingServlet extends HttpServlet {
	private static final long serialVersionUID = 6103678635240067321L;
	private static String ACTION_TYPE_ADD = "add";
	private static String ACTION_TYPE_QUERY = "query";
	private static String Action_TYPE_REMOVE = "remove";
	private static String Action_TYPE_JOIN = "join";
	private static String Action_TYPE_IWANTTOJOIN = "IWANTTOJOIN";
	private static String ACTION_TYPE_ALREADY = "already";
	private static String ACTION_TYPE_QUERY_ALREADY = "queryAlready";
	private static String ACTION_TYPE_CANCEL = "cancel";
	private static String ACTION_TYPE_QUERY_CAN_JOIN ="queryCanJoin";
	private static String ACTION_TYPE_REPORTPAGE = "reportPage";
	private static String Action_TYPE_REPORT = "report";
	private static String properties[] = { "meetingId", "name", "registerBegin", "registerEnd", "beginDate", "endDate", "money", "address",
			"host", "organizer", "co_organizer", "peopleNumber", "pay" };
	private MeetingService meetingService;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(User.SESSION_LOGIN_USER);
		meetingService = ServiceFactory.getInstance().createService(MeetingService.class,user);
		try {
			String action = request.getParameter("action");
			if (action == null || "".equals(action)) {
				request.getRequestDispatcher("/WEB-INF/page/meeting.jsp").forward(request, response);
			} else if (ACTION_TYPE_ADD.equals(action)) {
				String meetingId = request.getParameter("meetingId");
				if (meetingId == null || "".equals(meetingId)) {
					add(request, response);
				} else {
					update(request, response);
				}
			} else if (ACTION_TYPE_QUERY.equals(action)) {
				query(request, response);
			} else if (Action_TYPE_REMOVE.equals(action)) {
				remove(request, response);
			} else if (Action_TYPE_JOIN.equals(action)) {
				join(request, response);
			} else if (Action_TYPE_IWANTTOJOIN.equals(action)) {
				request.getRequestDispatcher("/WEB-INF/page/join.jsp").forward(request, response);
			} else if(ACTION_TYPE_ALREADY.equals(action)){
				request.getRequestDispatcher("/WEB-INF/page/already.jsp").forward(request, response);
			} else if(ACTION_TYPE_QUERY_ALREADY.equals(action)){
				queryAlready(request,response);
			} else if(ACTION_TYPE_CANCEL.equals(action)){
				cancel(request,response);
			} else if(ACTION_TYPE_QUERY_CAN_JOIN.equals(action)){
				queryCanJoin(request,response);
			} else if(ACTION_TYPE_REPORTPAGE.equals(action)){
				request.getRequestDispatcher("/WEB-INF/page/MeetingReport.jsp").forward(request, response);
			} else if(Action_TYPE_REPORT.equals(action)){
				report(request,response);
			}
		} catch (Exception e) {
			if (e instanceof java.lang.SecurityException) {
				JSONObject object = new JSONObject();
				object.put("message", Msg.NO_POWER);
				response.getWriter().write(object.toString());
			} else {
				e.printStackTrace();
				JSONObject object = new JSONObject();
				object.put("message", Msg.PROGRAM_ERROR);
				response.getWriter().write(object.toString());
			}
		}
	}
	private void report(HttpServletRequest request, HttpServletResponse response) {
		String meetingId = request.getParameter("meetingId");
		ReportConfig reportConfig = new ReportConfig();
			reportConfig.setReportParamsSql("SELECT * FROM T_Meeting WHERE meetingId = "+meetingId);
			reportConfig.setReportName("MEETING.grf");
			reportConfig.setReportDetailSql("SELECT * FROM T_User A LEFT JOIN User_Meeting B ON A.userId = B.userId WHERE B.meetingId = "+meetingId);
		String parameterData = "";
		if (reportConfig.getReportParamsSql() != null) {
			parameterData = GenXmlData.GenReportParameterData(reportConfig.getReportParamsSql());
		}
		response.setCharacterEncoding("utf-8");
		GenXmlData.GenFullReportData(response, reportConfig.getReportDetailSql(), parameterData, false);
	}

	/**
	 * @Description: 查询可以参加的会议
	 * @Create: 2014-3-3 下午4:12:49
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void queryCanJoin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		Integer r = null;
		Integer p = null;
		User user = (User) request.getSession().getAttribute(User.SESSION_LOGIN_USER);
		try {
			r = Integer.parseInt(rows);
			p = Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Meeting> list = meetingService.queryCanJoin(r, p,user.getUserId());
		/**
		 * 查询已报名的人的数量
		 */
		Long total = meetingService.queryCanJoinTotal(user.getUserId());
		String json = JSONUtil.toJson(list, properties, total);
		response.getWriter().write(json);
	}
	/**
	 * @Description: 取消参会
	 * @Create: 2014-3-3 下午4:12:53
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void cancel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String meetingId = request.getParameter("meetingId");
		Integer id = null;
		try {
			id = Integer.parseInt(meetingId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
			response.getWriter().write(result.toString());
			return;
		}
		User user = (User) request.getSession().getAttribute(User.SESSION_LOGIN_USER);
		if (user == null) {
			result.setMessage(Msg.PROGRAM_ERROR);
			response.getWriter().write(result.toString());
			return;
		}
		Integer userId = user.getUserId();
		result = meetingService.cancel(userId, id);
		response.getWriter().write(result.toString());
	}

	private void queryAlready(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		Integer r = null;
		Integer p = null;
		User user = (User) request.getSession().getAttribute(User.SESSION_LOGIN_USER);
		Integer userId = user.getUserId();
		try {
			r = Integer.parseInt(rows);
			p = Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Meeting> list = meetingService.queryAlready(r, p,userId);
		/**
		 * 查询已报名的人的数量
		 */
		Long total = meetingService.getAlreadyTotal(userId);
		String json = JSONUtil.toJson(list, properties, total);
		response.getWriter().write(json);
	}

	/**
	 * @Description: 用户参加会议
	 * @Create: 2014-2-20 下午4:42:26
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void join(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String meetingId = request.getParameter("meetingId");
		Integer id = null;
		try {
			id = Integer.parseInt(meetingId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
			response.getWriter().write(result.toString());
			return;
		}
		User user = (User) request.getSession().getAttribute(User.SESSION_LOGIN_USER);
		if (user == null) {
			result.setMessage(Msg.PROGRAM_ERROR);
			response.getWriter().write(result.toString());
			return;
		}
		Integer userId = user.getUserId();
		result = meetingService.join(userId, id);
		response.getWriter().write(result.toString());
	}

	/**
	 * @Description: 修改指定的Meeting对象
	 * @Create: 2014-2-16 下午3:44:41
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String name = request.getParameter("name");
		String registerBegin = request.getParameter("registerBegin");
		String registerEnd = request.getParameter("registerEnd");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String money = request.getParameter("money");
		String address = request.getParameter("address");
		String host = request.getParameter("host");
		String organizer = request.getParameter("organizer");
		String co_organizer = request.getParameter("co_organizer");
		String meetingId = request.getParameter("meetingId");
		String pay = request.getParameter("pay");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date bdate = null;
		Date edate = null;
		Date rbdate = null;
		Date redate = null;
		Double m = 0D;
		Integer id = null;
		try {
			bdate = format.parse(beginDate);
			edate = format.parse(endDate);
			rbdate = format.parse(registerBegin);
			redate = format.parse(registerEnd);
			m = Double.parseDouble(money);
			id = Integer.parseInt(meetingId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
			response.getWriter().write(result.toString());
			return;
		}
		Meeting meeting = new Meeting();
		meeting.setMeetingId(id);
		meeting.setAddress(address);
		meeting.setBeginDate(bdate);
		meeting.setCo_organizer(co_organizer);
		meeting.setEndDate(edate);
		meeting.setHost(host);
		meeting.setMoney(m);
		meeting.setName(name);
		meeting.setOrganizer(organizer);
		meeting.setRegisterBegin(rbdate);
		meeting.setRegisterEnd(redate);
		meeting.setCo_organizer(co_organizer);
		meeting.setPay(pay);
		result = meetingService.update(meeting);
		response.getWriter().write(result.toString());
	}

	/**
	 * @Description: 根据request 取得的meetingId删除Meeting对象
	 * @Create: 2014-2-16 下午3:23:18
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String meetingId = request.getParameter("meetingId");
		Integer id = null;
		try {
			id = Integer.parseInt(meetingId);
		} catch (Exception e) {
			result.setMessage(Msg.PROGRAM_ERROR);
		}

		result = meetingService.remove(id);
		response.getWriter().write(result.toString());
	}

	/**
	 * @Description: 查询所有会议记录
	 * @Create: 2014-2-15 下午6:22:25
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void query(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		String meetingName = request.getParameter("meetingName");
		Integer r = null;
		Integer p = null;
		try {
			r = Integer.parseInt(rows);
			p = Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Meeting meeting = new Meeting();
		meeting.setName(meetingName);
		List<Meeting> list = meetingService.getList(r, p,meeting);
		/**
		 * 查询已报名的人的数量
		 */
		Long total = meetingService.getTotal(meeting);
		String json = JSONUtil.toJson(list, properties, total);
		response.getWriter().write(json);
	}
	
	

	/**
	 * @Description: 发布一个新的会议
	 * @Create: 2014-2-15 下午12:44:14
	 * @author lhy
	 * @update logs
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult result = new ServiceResult(false);
		String name = request.getParameter("name");
		String registerBegin = request.getParameter("registerBegin");
		String registerEnd = request.getParameter("registerEnd");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String money = request.getParameter("money");
		String address = request.getParameter("address");
		String host = request.getParameter("host");
		String organizer = request.getParameter("organizer");
		String co_organizer = request.getParameter("co_organizer");
		String pay = request.getParameter("pay");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date bdate = null;
		Date edate = null;
		Date rbdate = null;
		Date redate = null;
		Double m = 0D;
		try {
			bdate = format.parse(beginDate);
			edate = format.parse(endDate);
			rbdate = format.parse(registerBegin);
			redate = format.parse(registerEnd);
			m = Double.parseDouble(money);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
			response.getWriter().write(result.toString());
			return;
		}
		Meeting meeting = new Meeting();
		meeting.setAddress(address);
		meeting.setBeginDate(bdate);
		meeting.setCo_organizer(co_organizer);
		meeting.setEndDate(edate);
		meeting.setHost(host);
		meeting.setMoney(m);
		meeting.setName(name);
		meeting.setOrganizer(organizer);
		meeting.setRegisterBegin(rbdate);
		meeting.setRegisterEnd(redate);
		meeting.setCo_organizer(co_organizer);
		meeting.setPay(pay);
		result = meetingService.add(meeting);
		response.getWriter().write(result.toString());
	}
}
