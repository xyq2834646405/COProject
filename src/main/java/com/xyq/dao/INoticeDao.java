package com.xyq.dao;

import com.xyq.entity.Notice;
import com.xyq.util.dao.IDao;

public interface INoticeDao extends IDao<Integer, Notice> {
    /**
     * 公告级别修改
     * @param snid 公告编号
     * @param level 修改的级别
     * @return 修改成功返回true,否则返回false
     * @throws Exception
     */
    public boolean doUpdateLevel(Integer snid,Integer level) throws Exception;
}
