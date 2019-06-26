package com.xyq.service.manager.impl;

import com.xyq.dao.INoticeDao;
import com.xyq.dao.IUserDao;
import com.xyq.dao.IUserNoticeDao;
import com.xyq.entity.Notice;
import com.xyq.entity.User;
import com.xyq.entity.UserNotice;
import com.xyq.service.manager.INoticeServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoticeServiceManagerImpl implements INoticeServiceManager {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private INoticeDao noticeDao;
    @Autowired
    private IUserNoticeDao userNoticeDao;

    public int unreadCount(String userid) throws Exception {
        User user = userDao.findById(userid);
        return noticeDao.getAllCountUnread(userid,user.getLevel());
    }

    public Map<String, Object> list(String userid,int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allNotices",noticeDao.findAllSplit(currentPage,lineSize,column,keyWord));
        map.put("noticeCount",noticeDao.getAllCount(column,keyWord));
        User user = userDao.findById(userid);
        map.put("unread",noticeDao.findUnread(userid,user.getLevel()));
        return map;
    }

    public Notice show(int snid, String userid) throws Exception {
        //读取用户的级别
        User user = userDao.findById(userid);
        //读取公告的完整信息
        Notice notice = noticeDao.findByIdAndLevel(snid,user.getLevel());
        //判断此公告是否已经读取过了
        if(!userNoticeDao.findByExists(userid,snid)){
            notice.setRnum(notice.getRnum()+1);//修改已有的阅读量
            UserNotice userNotice = new UserNotice();
            userNotice.setUser(user);
            userNotice.setNotice(notice);
            userNotice.setRdate(new Date());
            userNoticeDao.doCreate(userNotice);
        }
        return notice;
    }
}
