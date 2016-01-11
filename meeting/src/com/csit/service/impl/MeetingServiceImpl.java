package com.csit.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.csit.dao.MeetingDao;
import com.csit.entity.Meeting;
import com.csit.factory.DaoFactory;
import com.csit.service.MeetingService;
import com.csit.util.Msg;
import com.csit.util.Permission;
import com.csit.util.ServiceResult;

/**
 * @Description:会议service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class MeetingServiceImpl implements MeetingService {
	private MeetingDao meetingDao = DaoFactory.getInstance().createDao(MeetingDao.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.MeetingService#add(com.csit.entity.Meeting)
	 */
	@Override
	@Permission(Permission.ADMIN)
	public ServiceResult add(Meeting meeting) {
		ServiceResult result = new ServiceResult(false);
		/**
		 * 检测非空
		 */
		if (meeting.getName() == null || "".equals(meeting.getName())) {
			result.setMessage(Msg.PLEASE_INPUT_MEETING_NAME);
			return result;
		}
		if (meeting.getRegisterBegin() == null) {
			result.setMessage(Msg.PLEASE_SELECT_MEETING_REGISTER_BEGIN);
			return result;
		}
		if (meeting.getRegisterEnd() == null) {
			result.setMessage(Msg.PLEASE_SELECT_MEETING_REGISTER_END);
			return result;
		}
		if(meeting.getRegisterBegin().getTime()>=meeting.getRegisterEnd().getTime()){
			result.setMessage(Msg.REGISTER_BEGIN_CANNOT_EXCESS);
			return result;
		}
		if (meeting.getBeginDate() == null ) {
			result.setMessage(Msg.PLEASE_SELECT_MEETING_BEGIN);
			return result;
		}
		if (meeting.getEndDate() == null) {
			result.setMessage(Msg.PLEASE_SELECT_MEETING_END);
			return result;
		}
	
		if(meeting.getBeginDate().getTime()>=meeting.getEndDate().getTime()){
			result.setMessage(Msg.MEETING_BEGIN_CANNOT_EXCESS);
			return result;
		}
		if(meeting.getBeginDate().getTime()<meeting.getRegisterEnd().getTime()){
			result.setMessage(Msg.REGISTER_END_CANNOT_EXCESS);
			return result;
		}
		if (meeting.getMoney() == null || "".equals(meeting.getMoney())) {
			result.setMessage(Msg.PLEASE_INPUT_MONEY);
			return result;
		}
		if (meeting.getAddress() == null || "".equals(meeting.getAddress())) {
			result.setMessage(Msg.PLEASE_INPUT_MEETING_ADDRESS);
			return result;
		}
		if (meeting.getHost() == null || "".equals(meeting.getHost())) {
			result.setMessage(Msg.PLEASE_INPUT_HOST);
			return result;
		}
		meeting.setPeopleNumber(0);

		try {
			Integer count = meetingDao.add(meeting);
			if (count == 0) {
				result.setMessage(Msg.PROGRAM_ERROR);
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		result.setSuccess(true);
		result.setMessage(Msg.ADD_SUCCESS);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.MeetingService#getList(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Meeting> getList(Integer rows, Integer page,Meeting model) {
		List<Meeting> list = meetingDao.getList(rows, page,model);
		Integer[] array = new Integer[list.size()];
		for (int i = 0; i < list.size(); i++) {

			Meeting meeting = list.get(i);
			Integer id = meeting.getMeetingId();
			array[i] = id;
		}
		List counts = meetingDao.queryRegisterCount(array);
		for (Meeting meeting : list) {
			meeting.setPeopleNumber(0);
			Iterator it = counts.iterator();
			while (it.hasNext()) {
				Object[] value = (Object[]) it.next();
				if (value.length == 2) {
					Integer count = (Integer) value[0];
					Integer meetingId = (Integer) value[1];
					if (meeting.getMeetingId() != null && meeting.getMeetingId().equals(meetingId)) {
						meeting.setPeopleNumber(count);
					}
				}
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.MeetingService#getTotal()
	 */
	@Override
	public Long getTotal(Meeting meeting) {
		return meetingDao.getTotal(meeting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.MeetingService#remove(java.lang.Integer)
	 */
	@Override
	@Permission(Permission.ADMIN)
	public ServiceResult remove(Integer id) {
		ServiceResult result = new ServiceResult(false);
		if (id == null) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		/**
		 * 对于已经开始的会议，不允许删除，只有当会议结束以后，才允许删除 （PS：如果没有该要求，请删除以下 begin 至 end 段的代码）
		 */
		// begin
		Meeting meeting = meetingDao.getById(id);
		if (meeting == null) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		Date bdate = meeting.getBeginDate();
		Date edate = meeting.getEndDate();
		Long now = System.currentTimeMillis();
		if (now < edate.getTime() && bdate.getTime() < now) {
			result.setMessage(Msg.DELETE_MEETING_ERROR);
			return result;
		}
		// end
		Integer count = meetingDao.remove(id);
		if (count == 0) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		result.setSuccess(true);
		result.setMessage(Msg.DELETE_SUCCESS);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.MeetingService#update(com.csit.entity.Meeting)
	 */
	@Override
	@Permission(Permission.ADMIN)
	public ServiceResult update(Meeting meeting) {
		ServiceResult result = new ServiceResult(false);
		if (meeting.getName() == null || "".equals(meeting.getName())) {
			result.setMessage(Msg.PLEASE_INPUT_MEETING_NAME);
			return result;
		}
		if (meeting.getRegisterBegin() == null || "".equals(meeting.getRegisterBegin())) {
			result.setMessage(Msg.PLEASE_SELECT_MEETING_REGISTER_BEGIN);
			return result;
		}
		if (meeting.getRegisterEnd() == null || "".equals(meeting.getRegisterEnd())) {
			result.setMessage(Msg.PLEASE_SELECT_MEETING_REGISTER_END);
			return result;
		}
		if (meeting.getBeginDate() == null || "".equals(meeting.getBeginDate())) {
			result.setMessage(Msg.PLEASE_SELECT_MEETING_BEGIN);
			return result;
		}
		if (meeting.getEndDate() == null || "".equals(meeting.getEndDate())) {
			result.setMessage(Msg.PLEASE_SELECT_MEETING_END);
			return result;
		}
		if (meeting.getMoney() == null || "".equals(meeting.getMoney())) {
			result.setMessage(Msg.PLEASE_INPUT_MONEY);
			return result;
		}
		if (meeting.getAddress() == null || "".equals(meeting.getAddress())) {
			result.setMessage(Msg.PLEASE_INPUT_MEETING_ADDRESS);
			return result;
		}
		if (meeting.getHost() == null || "".equals(meeting.getHost())) {
			result.setMessage(Msg.PLEASE_INPUT_HOST);
			return result;
		}
		Meeting model = meetingDao.getById(meeting.getMeetingId());
		if (model == null) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		model.setAddress(meeting.getAddress());
		model.setBeginDate(meeting.getBeginDate());
		model.setEndDate(meeting.getEndDate());
		model.setCo_organizer(meeting.getCo_organizer());
		model.setHost(meeting.getHost());
		model.setMoney(meeting.getMoney());
		model.setName(meeting.getName());
		model.setOrganizer(meeting.getOrganizer());
		model.setPay(meeting.getPay());
		model.setRegisterBegin(meeting.getRegisterBegin());
		model.setRegisterEnd(meeting.getRegisterEnd());
		model.setPay(meeting.getPay());
		Integer count = meetingDao.update(model);
		if (count == 0) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		} else {
			result.setSuccess(true);
			result.setMessage(Msg.UPDATE_SUCCESS);
			return result;
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.service.MeetingService#join(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public ServiceResult join(Integer userId, Integer meetingId) {
		ServiceResult result = new ServiceResult(false);
		if (userId == null || "".equals(userId) || meetingId == null || "".equals(meetingId)) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		/**
		 * 检查会议是否可以报名
		 */
		Long currentDate = System.currentTimeMillis();
		Meeting meeting = meetingDao.getById(meetingId);
		if (currentDate < meeting.getRegisterBegin().getTime() || currentDate > meeting.getRegisterEnd().getTime()) {
			result.setMessage(Msg.REGISTER_MEETING_ERROR);
			return result;
		}

		/**
		 * 检查是否已经报名
		 */
		boolean flag = meetingDao.IsJoin(meetingId, userId);
		if (flag) {
			result.setMessage(Msg.MEETING_ALREADY_REGISTER);
			return result;
		}
		try {
			Integer count = meetingDao.join(meetingId, userId);
			if (count == 0) {
				result.setMessage(Msg.PROGRAM_ERROR);
				return result;
			} else {
				result.setMessage(Msg.REGISTER_MEETING_SUCCESS);
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Meeting> queryAlready(Integer r, Integer p, Integer userId) {
		List<Meeting> list = meetingDao.queryAlready(r, p, userId);
		Integer[] array = new Integer[list.size()];
		for (int i = 0; i < list.size(); i++) {

			Meeting meeting = list.get(i);
			Integer id = meeting.getMeetingId();
			array[i] = id;
		}
		List counts = meetingDao.queryRegisterCount(array);
		for (Meeting meeting : list) {
			meeting.setPeopleNumber(0);
			Iterator it = counts.iterator();
			while (it.hasNext()) {
				Object[] value = (Object[]) it.next();
				if (value.length == 2) {
					Integer count = (Integer) value[0];
					Integer meetingId = (Integer) value[1];
					if (meeting.getMeetingId() != null && meeting.getMeetingId().equals(meetingId)) {
						meeting.setPeopleNumber(count);
					}
				}
			}
		}
		return list;
	}

	@Override
	public Long getAlreadyTotal(Integer userId) {
		return meetingDao.getAlreadyTotal(userId);
	}

	@Override
	public ServiceResult cancel(Integer userId, Integer meetingId) {
		ServiceResult result = new ServiceResult(false);
		if (userId == null || "".equals(userId) || meetingId == null || "".equals(meetingId)) {
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
		/**
		 * 检查会议是否已经过了报名期
		 */
		Long currentDate = System.currentTimeMillis();
		Meeting meeting = meetingDao.getById(meetingId);
		if (currentDate < meeting.getRegisterBegin().getTime() || currentDate > meeting.getRegisterEnd().getTime()) {
			result.setMessage(Msg.MEETING_CANNOT_CANCEL);
			return result;
		}

		try {
			Integer count = meetingDao.calcel(meetingId, userId);
			if (count == 0) {
				result.setMessage(Msg.PROGRAM_ERROR);
				return result;
			} else {
				result.setMessage(Msg.MEETING_CANCEL_SUCCESS);
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.setMessage(Msg.PROGRAM_ERROR);
			return result;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Meeting> queryCanJoin(Integer rows, Integer page, Integer userId) {
		List<Meeting> list = meetingDao.queryCanJoin(rows, page, userId);
		Integer[] array = new Integer[list.size()];
		for (int i = 0; i < list.size(); i++) {

			Meeting meeting = list.get(i);
			Integer id = meeting.getMeetingId();
			array[i] = id;
		}
		List counts = meetingDao.queryRegisterCount(array);
		for (Meeting meeting : list) {
			meeting.setPeopleNumber(0);
			Iterator it = counts.iterator();
			while (it.hasNext()) {
				Object[] value = (Object[]) it.next();
				if (value.length == 2) {
					Integer count = (Integer) value[0];
					Integer meetingId = (Integer) value[1];
					if (meeting.getMeetingId() != null && meeting.getMeetingId().equals(meetingId)) {
						meeting.setPeopleNumber(count);
					}
				}
			}
		}
		return list;

	}

	@Override
	public Long queryCanJoinTotal(Integer userId) {
		return meetingDao.queryCanJoinTotal(userId);
	}
}
