package com.xyq.dao.impl;

import com.xyq.dao.IDoctypeDao;
import com.xyq.entity.Doctype;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class DoctypeDaoImpl extends AbstractDaoImpl implements IDoctypeDao {
    public boolean doCreate(Doctype vo) throws SQLException {
        return getSession().save(vo)!=null;
    }

    public boolean doUpdate(Doctype vo) throws SQLException {
        String hql = "update Doctype set title=? where dtid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getTitle());
        query.setParameter(1,vo.getDtid());
        return query.executeUpdate()>0;
    }

    public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
        return false;
    }

    public Doctype findById(Integer id) throws SQLException {
        return null;
    }

    public List<Doctype> findAll() throws SQLException {
        return handleList(Doctype.class);
    }

    public List<Doctype> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return null;
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return null;
    }
}
