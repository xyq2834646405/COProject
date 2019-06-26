package com.xyq.dao.impl;

import com.xyq.dao.IActionDao;
import com.xyq.entity.Action;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class ActionDaoImpl extends AbstractDaoImpl implements IActionDao {
    public List<Action> findAllByGroups(Integer gid) throws Exception {
        String hql = "from Action as a where a.groups.gid=?";
        Query query = getQuery(hql);
        query.setParameter(0,gid);
        return query.list();
    }

    public boolean doCreate(Action vo) throws SQLException {
        return false;
    }

    public boolean doUpdate(Action vo) throws SQLException {
        String hql = "update Action as a set a.title=?,a.url=? where a.actid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getTitle());
        query.setParameter(1,vo.getUrl());
        query.setParameter(2,vo.getActid());
        return query.executeUpdate()>0;
    }

    public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
        return false;
    }

    public Action findById(Integer id) throws SQLException {
        return null;
    }

    public List<Action> findAll() throws SQLException {
        return handleList(Action.class);
    }

    public List<Action> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return handleListSplit(Action.class,currentPage,lineSize,column,keyWord);
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return handleCount("Action",column,keyWord);
    }
}
