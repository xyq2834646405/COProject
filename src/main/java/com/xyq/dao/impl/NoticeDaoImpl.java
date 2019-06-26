package com.xyq.dao.impl;

import com.xyq.dao.INoticeDao;
import com.xyq.entity.Notice;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class NoticeDaoImpl extends AbstractDaoImpl implements INoticeDao {
    public boolean doCreate(Notice vo) throws SQLException {
        return getSession().save(vo)!=null;
    }

    public boolean doUpdate(Notice vo) throws SQLException {
        String hql = "update Notice set title=?,content=?,level=? where snid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getTitle());
        query.setParameter(1,vo.getContent());
        query.setParameter(2,vo.getLevel());
        query.setParameter(3,vo.getSnid());
        return query.executeUpdate() > 0;
    }

    public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
        return handleRemoveBatch("Notice","snid",ids);
    }

    public Notice findById(Integer id) throws SQLException {
        return getSession().get(Notice.class,id);
    }

    public List<Notice> findAll() throws SQLException {
        return handleList(Notice.class);
    }

    public List<Notice> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return handleListSplit(Notice.class,currentPage,lineSize,column,keyWord);
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return handleCount("Notice",column,keyWord);
    }

    public boolean doUpdateLevel(Integer snid, Integer level) throws Exception {
        String hql = "update Notice set level=? where snid=?";
        Query query = getQuery(hql);
        query.setParameter(0,level);
        query.setParameter(1,snid);
        return query.executeUpdate() > 0;
    }
}
