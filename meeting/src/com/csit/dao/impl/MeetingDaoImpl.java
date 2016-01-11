package com.csit.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.csit.dao.MeetingDao;
import com.csit.entity.Meeting;
import com.csit.util.JdbcUtils;
import com.csit.util.WrapDate;

/**
 * @Description: 会议dao实现类
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class MeetingDaoImpl implements MeetingDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.MeetingDao#add(com.csit.entity.Meeting)
	 */
	@Override
	public Integer add(Meeting meeting) throws SQLException {
		java.sql.Date begin = new WrapDate(meeting.getBeginDate().getTime());
		java.sql.Date end = new WrapDate(meeting.getEndDate().getTime());
		java.sql.Date rbegin = new WrapDate(meeting.getRegisterBegin().getTime());
		java.sql.Date rend = new WrapDate(meeting.getRegisterEnd().getTime());
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "insert into T_Meeting ([name],registerBegin,registerEnd,"
				+ "beginDate,endDate,[money],[address],[host],organizer,co_organizer,peopleNumber,pay) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		Object params[] = { meeting.getName(), rbegin.toString(), rend.toString(), begin.toString(), end.toString(),
				meeting.getMoney(), meeting.getAddress(), meeting.getHost(), meeting.getOrganizer(), meeting.getCo_organizer(),
				meeting.getPeopleNumber(), meeting.getPay() };
		Integer count = runner.update(sql, params);
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.MeetingDao#getList(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Meeting> getList(Integer rows, Integer page,Meeting model) {
		
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		page = (rows * (page - 1));
		StringBuffer buffer = new StringBuffer();
		buffer.append("select top(" + rows + ") * from T_Meeting  where 1=1 ");
		if(model!=null && model.getName()!=null){
			buffer.append(" and name like '%"+model.getName()+"%' ");
		}
		buffer.append(" and (meetingId not in( select top(" + page + ") meetingId from T_Meeting where 1=1 ");
		if(model!=null && model.getName()!=null){
			buffer.append(" and name like '%"+model.getName()+"%' ");
		}
		buffer.append("order by meetingId desc))order by meetingId desc ");
		List<Meeting> list = null;
		try {
			list = (List<Meeting>) runner.query(buffer.toString(), new BeanListHandler(Meeting.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.MeetingDao#getTotal()
	 */
	@Override
	public Long getTotal(Meeting meeting) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from T_Meeting ";
			if(meeting!=null && meeting.getName()!=null){
				sql +="where name like '%"+meeting.getName()+"%'";
			}
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
	 * 
	 * @see com.csit.dao.MeetingDao#remove(java.lang.Integer)
	 */
	@Override
	public Integer remove(Integer id) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "delete from T_Meeting where meetingId = ?";
			String sql2 = "delete from User_Meeting where meetingId = ?";
			int count = runner.update(sql, id);
			runner.update(sql2, id);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.MeetingDao#getById(java.lang.Integer)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Meeting getById(Integer id) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from T_Meeting where meetingId = ? ";
			Meeting model = (Meeting) runner.query(sql, id, new BeanHandler(Meeting.class));
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.MeetingDao#update(com.csit.entity.Meeting)
	 */
	@Override
	public Integer update(Meeting meeting) {
		java.sql.Date begin = new WrapDate(meeting.getBeginDate().getTime());
		java.sql.Date end = new WrapDate(meeting.getEndDate().getTime());
		java.sql.Date rbegin = new WrapDate(meeting.getRegisterBegin().getTime());
		java.sql.Date rend = new WrapDate(meeting.getRegisterEnd().getTime());
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update T_Meeting set name = ? , registerBegin = ? , registerEnd = ? , beginDate = ? , endDate = ? , "
					+ " money = ? , address = ? , host = ? , organizer = ? , co_organizer = ? , pay = ? " + " where meetingId = ? ";
			Object params[] = { meeting.getName(), rbegin.toString(), rend.toString(), begin.toString(), end.toString(), meeting.getMoney(),
					meeting.getAddress(), meeting.getHost(), meeting.getOrganizer(), meeting.getCo_organizer(), meeting.getPay(),
					meeting.getMeetingId() };
			return runner.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.MeetingDao#join(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer join(Integer meetingId, Integer userId) throws SQLException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "insert into User_Meeting (userId , meetingId) values ( ? , ? )";
		Object params[] = { userId, meetingId };
		return runner.update(sql, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.MeetingDao#IsJoin(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean IsJoin(Integer meetingId, Integer userId) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select count(*) from User_Meeting where userId = ? and meetingId = ?";
		Object params[] = { userId, meetingId };
		try {
			@SuppressWarnings("deprecation")
			Object[] obj = (Object[]) runner.query(sql, params, new ArrayHandler());
			if (obj.length == 1) {
				Integer count = (Integer) obj[0];
				if (count == 0) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.MeetingDao#queryRegisterCount(java.lang.Integer)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List queryRegisterCount(Integer[] array) {
		String s = "";
		if(array !=null){
			for(int i = 0 ;i<array.length;i++){
				s += array[i]+",";
			}
		}
		if(s.length()>0){
			s= s.substring(0, s.length()-1);
		}
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			
			String sql = "SELECT COUNT(*),meetingId FROM User_Meeting where 1=1";
			if(!"".equals(s)){
				sql +=" and meetingId in ("+s+") ";
			}
			sql +="  GROUP BY meetingId  ";
			List list = (List) runner.query(sql, new ArrayListHandler());
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meeting> queryAlready(Integer rows, Integer page, Integer userId) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		page = (rows * (page - 1));
		StringBuffer buffer = new StringBuffer();
		buffer.append("select top(" + rows + ")c.* from T_Meeting c left join User_Meeting d on c.meetingId = d.meetingId where d.userId = '" + userId
				+ "'  ");
		buffer.append(" and( c.meetingId not in(  select  top(" + page + ") a.meetingId  ");
		buffer.append(" from T_Meeting a left join User_Meeting b on a.meetingId = b.meetingId where b.userId = '" + userId+"'  group by a.meetingId order by a.meetingId desc)) group by c.meetingId,c.address,c.beginDate,c.name,c.co_organizer,c.endDate,c.host,c.money,c.organizer,c.pay,c.peopleNumber,c.registerBegin ");
		buffer.append(",c.registerEnd  order by c.meetingId desc ");
		List<Meeting> list = null;
		try {
			list = (List<Meeting>) runner.query(buffer.toString(), new BeanListHandler(Meeting.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	@Override
	public Long getAlreadyTotal(Integer userId) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from T_Meeting a left join User_Meeting b on a.meetingId = b.meetingId where b.userId = " + userId;
			Object[] count = (Object[]) runner.query(sql, new ArrayHandler());
			if (count.length > 0) {
				return Long.parseLong(count[0].toString());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return 0L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.csit.dao.MeetingDao#calcel(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer calcel(Integer meetingId, Integer userId) throws SQLException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "delete from User_Meeting where meetingId = ? and userId = ? ";
		Object params[] = { meetingId, userId };
		return runner.update(sql, params);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public List<Meeting> queryCanJoin(Integer rows, Integer page, Integer userId) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		page = (rows * (page - 1));
		String sql = "select meetingId from User_Meeting  where userId = ? group by meetingId";
		List idList = null;
		try {
			idList = ((List) runner.query(sql, userId, new ArrayListHandler()));
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		Iterator it = idList.iterator();
		String s = "";
		while(it.hasNext()){
			Object[] obj = (Object[]) it.next();
			if(obj.length>0){
				s += obj[0].toString()+",";
			}
		}
		if (s.length() > 0) {
			s = s.substring(0, s.length() - 1);
		}
		StringBuffer buffer = new StringBuffer();
		Long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String currentDate = format.format(date);
		buffer.append("select top("+rows+")c.* from T_Meeting c left join User_Meeting d on c.meetingId = d.meetingId where 1=1 and c.registerBegin< cast( '"+currentDate+"' as datetime) and c.registerEnd > cast ('"+currentDate+"' as datetime) ");
		if (!"".equals(s)) {
			buffer.append(" and (d.meetingId is null or d.meetingId not in ("+s+"))");
		}
		buffer.append(" and( c.meetingId not in(  select  top(" + page + ") e.meetingId  ");
		buffer.append(" from T_Meeting e left join User_Meeting f on e.meetingId = f.meetingId where 1=1 and e.registerBegin < cast('"+currentDate+"' as datetime) and e.registerEnd > cast('"+currentDate+"' as datetime) ");
		if (!"".equals(s)) {
			buffer.append(" and f.meetingId is null or  f.meetingId not in ("+s+") ");
		}
		buffer.append(" group by e.meetingId order by e.meetingId desc)) group by c.meetingId,c.address,c.beginDate,c.name,c.co_organizer,c.endDate,c.host,c.money,c.organizer,c.pay,c.peopleNumber,c.registerBegin ");
		buffer.append("	,c.registerEnd order by c.meetingId desc");
			
		List<Meeting> list = null;
		try {
			list = (List<Meeting>) runner.query(buffer.toString(), new BeanListHandler(Meeting.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	@Override
	public Long queryCanJoinTotal(Integer userId) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select meetingId from User_Meeting  where userId = ? group by meetingId";
			List idList = null;
			try {
				idList = (List) runner.query(sql, userId, new ArrayListHandler());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			Iterator it = idList.iterator();
			String s = "";
			while(it.hasNext()){
				Object[] obj = (Object[]) it.next();
				if(obj.length>0){
					s += obj[0].toString()+",";
				}
			}
			if (s.length() > 0) {
				s = s.substring(0, s.length() - 1);
			}
			Long time = System.currentTimeMillis();
			Date date = new Date(time);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String currentDate = format.format(date);
			sql = "select count(*) from T_Meeting a left join User_Meeting b on a.meetingId = b.meetingId where 1=1 and a.registerBegin< cast('"+currentDate+"' as datetime) and a.registerEnd > cast('"+currentDate+"' as datetime) ";
			if(!"".equals(s)){
				sql+= " and a.meetingId not in ("+s+")";
			}
			Object[] count = (Object[]) runner.query(sql, new ArrayHandler());
			if (count.length > 0) {
				return Long.parseLong(count[0].toString());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return 0L;
	}
}
