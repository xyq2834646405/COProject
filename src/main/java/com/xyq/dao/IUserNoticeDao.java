package com.xyq.dao;

import com.xyq.entity.UserNotice;
import com.xyq.util.dao.IDao;

public interface IUserNoticeDao {
    /**
     * 增加用户的阅读记录信息
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean doCreate(UserNotice vo) throws Exception;

    /**
     * 判断用户的阅读记录是否已经存在
     * @param userid
     * @param snid
     * @return 如果存在返回true,否则返回false
     * @throws Exception
     */
    public boolean findByExists(String userid,Integer snid) throws Exception;
}
