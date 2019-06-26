package com.xyq.dao.impl;

import com.xyq.dao.IUserNoticeDao;
import com.xyq.entity.UserNotice;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

@Component
public class UserNoticeDaoImpl extends AbstractDaoImpl implements IUserNoticeDao {
    public boolean doCreate(UserNotice vo) throws Exception {
        String sql = "insert into user_notice(userid,snid,rdate) values(?,?,?)";
        Query query = getSQLQuery(sql);
        query.setParameter(0,vo.getUser().getUserid());
        query.setParameter(1,vo.getNotice().getSnid());
        query.setParameter(2,vo.getRdate());
        return query.executeUpdate() > 0;
    }

    public boolean findByExists(String userid, Integer snid) throws Exception {
        String sql = "select count(*) from user_notice where userid=? and snid=?";
        Query query = getSQLQuery(sql);
        query.setParameter(0,userid);
        query.setParameter(1,snid);
        return ((Long)query.uniqueResult()) > 0;
    }
}
