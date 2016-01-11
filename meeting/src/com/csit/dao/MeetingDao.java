package com.csit.dao;

import java.sql.SQLException;
import java.util.List;

import com.csit.entity.Meeting;
/**
 * @Description: 会议DAO
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public interface MeetingDao {
	/**
	 * @Description: 添加   返回影响的行数
	 * @Create: 2014-2-20 下午4:32:32
	 * @author lhy
	 * @update logs
	 * @param meeting
	 * @return
	 * @throws SQLException
	 */
	Integer add(Meeting meeting) throws SQLException;
	/**
	 * @Description: 分页查询
	 * @Create: 2014-2-20 下午4:32:52
	 * @author lhy
	 * @update logs
	 * @param rows
	 * @param page
	 * @param model 
	 * @return
	 * @throws SQLException
	 */
	List<Meeting> getList(Integer rows, Integer page, Meeting model);
	/**
	 * @Description: 获得总记录数
	 * @Create: 2014-2-20 下午4:33:02
	 * @author lhy
	 * @param meeting 
	 * @update logs
	 * @return
	 */
	Long getTotal(Meeting meeting);
	/**
	 * @Description: 根据ID删除
	 * @Create: 2014-2-20 下午4:33:11
	 * @author lhy
	 * @update logs
	 * @param id
	 * @return
	 */
	Integer remove(Integer id);
	/**
	 * @Description: 根据ID查询对象 查不到则返回null
	 * @Create: 2014-2-20 下午4:33:20
	 * @author lhy
	 * @update logs
	 * @param id
	 * @return
	 */
	Meeting getById(Integer id);
	/**
	 * @Description: 更新
	 * @Create: 2014-2-20 下午4:33:51
	 * @author lhy
	 * @update logs
	 * @param meeting
	 * @return
	 */
	Integer update(Meeting meeting);
	/**
	 * @Description: 参加会议 
	 * @Create: 2014-2-20 下午4:33:58
	 * @author lhy
	 * @update logs
	 * @param meetingId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	Integer join(Integer meetingId, Integer userId) throws SQLException;
	/**
	 * @Description: 判断用户是否已经参加过这个会议
	 * @Create: 2014-2-20 下午4:34:16
	 * @author lhy
	 * @update logs
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	boolean IsJoin(Integer meetingId, Integer userId);
	/**
	 * @Description: 得到参会人数
	 * @Create: 2014-2-20 下午4:34:38
	 * @author lhy
	 * @update logs
	 * @param array
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List queryRegisterCount(Integer[] array);
	/**
	 * @Description: 查询已经报名的会议
	 * @Create: 2014-3-3 下午2:58:36
	 * @author lhy
	 * @update logs
	 * @param r
	 * @param p
	 * @param userId
	 * @return
	 */
	List<Meeting> queryAlready(Integer r, Integer p, Integer userId);
	/**
	 * @Description: 已经报名的会议的总数量
	 * @Create: 2014-3-3 下午3:24:21
	 * @author lhy
	 * @update logs
	 * @param userId
	 * @return
	 */
	Long getAlreadyTotal(Integer userId);
	/**
	 * @Description: 取消报名会议
	 * @Create: 2014-3-3 下午3:38:02
	 * @author lhy
	 * @update logs
	 * @param meetingId
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	Integer calcel(Integer meetingId, Integer userId) throws SQLException;
	/**
	 * @Description: 查询能参加的会议
	 * @Create: 2014-3-3 下午4:16:28
	 * @author lhy
	 * @update logs
	 * @param rows
	 * @param page
	 * @param userId
	 * @return
	 */
	List<Meeting> queryCanJoin(Integer rows, Integer page, Integer userId);
	/**
	 * @Description: 查询能参加的会议的总数
	 * @Create: 2014-3-3 下午4:19:51
	 * @author lhy
	 * @update logs
	 * @param userId
	 * @return
	 */
	Long queryCanJoinTotal(Integer userId);

}
