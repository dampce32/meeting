package com.csit.service;

import java.util.List;

import com.csit.entity.Meeting;
import com.csit.util.ServiceResult;
/**
 * @Description:会议Service
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public interface MeetingService {
	/**
	 * @Description: 添加
	 * @Create: 2014-2-20 下午4:29:05
	 * @author lhy
	 * @update logs
	 * @param meeting
	 * @return
	 */
	ServiceResult add(Meeting meeting);
	/**
	 * @Description: 分页查询
	 * @Create: 2014-2-20 下午4:29:11
	 * @author lhy
	 * @update logs
	 * @param r
	 * @param p
	 * @param meeting 
	 * @return
	 */
	List<Meeting> getList(Integer r, Integer p, Meeting meeting);
	/**
	 * @Description: 得到总记录数
	 * @Create: 2014-2-20 下午4:29:17
	 * @author lhy
	 * @param meeting 
	 * @update logs
	 * @return
	 */
	Long getTotal(Meeting meeting);
	/**
	 * @Description: 根据ID删除
	 * @Create: 2014-2-20 下午4:29:27
	 * @author lhy
	 * @update logs
	 * @param id
	 * @return
	 */
	ServiceResult remove(Integer id);
	/**
	 * @Description: 更新
	 * @Create: 2014-2-20 下午4:29:37
	 * @author lhy
	 * @update logs
	 * @param meeting
	 * @return
	 */
	ServiceResult update(Meeting meeting);
	/**
	 * @Description: 参加指定的会议
	 * @Create: 2014-3-3 下午2:57:12
	 * @author lhy
	 * @update logs
	 * @param userId
	 * @param meetingId
	 * @return
	 */
	ServiceResult join(Integer userId, Integer meetingId);
	/**
	 * @Description: 查询已经报名的会议
	 * @Create: 2014-3-3 下午2:57:09
	 * @author lhy
	 * @update logs
	 * @param r
	 * @param p
	 * @param userId
	 * @return
	 */
	List<Meeting> queryAlready(Integer r, Integer p, Integer userId);
	/**
	 * @Description: 查询已经报名的会议的总数
	 * @Create: 2014-3-3 下午3:23:17
	 * @author lhy
	 * @update logs
	 * @param userId
	 * @return
	 */
	Long getAlreadyTotal(Integer userId);
	/**
	 * @Description: 取消参会
	 * @Create: 2014-3-3 下午3:35:18
	 * @author lhy
	 * @update logs
	 * @param userId
	 * @param id
	 * @return
	 */
	ServiceResult cancel(Integer userId, Integer id);
	/**
	 * @Description: 查询能参加的会议
	 * @Create: 2014-3-3 下午4:15:16
	 * @author lhy
	 * @update logs
	 * @param r
	 * @param p
	 * @param userId
	 * @return
	 */
	List<Meeting> queryCanJoin(Integer r, Integer p, Integer userId);
	/**
	 * @Description: 查询能参加会议的总数
	 * @Create: 2014-3-3 下午4:18:04
	 * @author lhy
	 * @param integer 
	 * @update logs
	 * @return
	 */
	Long queryCanJoinTotal(Integer integer);

}
