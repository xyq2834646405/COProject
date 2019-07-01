package com.xyq.dao.impl;

import com.xyq.dao.ITasktypeDao;
import com.xyq.entity.Tasktype;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class TasktypeDaoImpl extends AbstractDaoImpl implements ITasktypeDao {
    public boolean doCreate(Tasktype vo) throws SQLException {
        return getSession().save(vo)!=null;
    }

    public boolean doUpdate(Tasktype vo) throws SQLException {
        String hql = "update Tasktype set title=? where ttid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getTitle());
        query.setParameter(1,vo.getTtid());
        return query.executeUpdate()>0;
    }

    public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
        return false;
    }

    public Tasktype findById(Integer id) throws SQLException {
        return null;
    }

    public List<Tasktype> findAll() throws SQLException {
        return handleList(Tasktype.class);
    }

    public List<Tasktype> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return null;
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return null;
    }
}