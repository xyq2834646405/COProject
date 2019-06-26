package com.xyq.service.emp.impl;

import com.xyq.dao.INoticeDao;
import com.xyq.dao.IUserDao;
import com.xyq.dao.IUserNoticeDao;
import com.xyq.entity.User;
import com.xyq.service.emp.IIndexServiceEmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IndexServiceEmpImpl implements IIndexServiceEmp {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private INoticeDao noticeDao;
    @Autowired
    private IUserNoticeDao userNoticeDao;

    public Map<String,Integer> unreadCount(String userid) throws Exception {
        Map<String,Integer> map = new HashMap<String, Integer>();
        User user = userDao.findById(userid);
        map.put("noticeCount",noticeDao.getAllCountUnread(userid,user.getLevel()));
        return map;
    }
}
