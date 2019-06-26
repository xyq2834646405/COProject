package com.xyq.dao;

import com.xyq.entity.Notice;
import com.xyq.util.dao.IDao;

import java.util.List;
import java.util.Map;

public interface INoticeDao extends IDao<Integer, Notice> {
    /**
     * 公告级别修改
     * @param snid 公告编号
     * @param level 修改的级别
     * @return 修改成功返回true,否则返回false
     * @throws Exception
     */
    public boolean doUpdateLevel(Integer snid,Integer level) throws Exception;

    /**
     * 读取没有阅读过的公告的编号数据,目的是在页面显示
     * @param userid 要判断的用户ID
     * @return key = 公告的编号、value = 是否阅读(true)
     * @throws Exception
     */
    public Map<Integer,Boolean> findUnread(String userid,Integer level) throws Exception;

    /**
     * 根据用户的编号以及用户的级别读取出未读的公告数据量
     * @param userid 用户编号
     * @param level 用户级别
     * @return 返回统计的数据量信息
     * @throws Exception
     */
    public Integer getAllCountUnread(String userid,Integer level) throws Exception;

    /**
     * 根据公告编号以及用户的级别读取信息
     * @param id
     * @param level
     * @return
     * @throws Exception
     */
    public Notice findByIdAndLevel(Integer id,Integer level) throws Exception;

    /**
     * 根据级别查询公告信息
     * @param level 查询的级别
     * @param currentPage 当前页
     * @param lineSize 显示行数
     * @param column 模糊查询关键列
     * @param keyWord 模糊查询关键字
     * @return
     * @throws Exception
     */
    public List<Notice> findAllSplitByLevel(Integer level,Integer currentPage,Integer lineSize,String column,String keyWord) throws Exception;

    /**
     * 根据级别查询公告信息数据量
     * @param level 查询的级别
     * @param column 模糊查询关键列
     * @param keyWord 模糊查询关键字
     * @return
     * @throws Exception
     */
    public Integer getAllCountByLevel(Integer level,String column,String keyWord) throws Exception;
}
