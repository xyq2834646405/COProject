package com.xyq.dao.impl;

import com.xyq.dao.IGroupsDao;
import com.xyq.entity.Groups;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class GroupsDaoImpl extends AbstractDaoImpl implements IGroupsDao {
    public List<Groups> findAllByRole(Integer rid) throws Exception {
        String sql = "select gid,title,note from groups where gid in(select gid from role_groups where rid = ?)";
        Query query = getSQLQuery(sql);
        query.setResultTransformer(new AliasToBeanResultTransformer(Groups.class));
        query.setParameter(0,rid);
        return query.list();
    }

    public boolean doCreate(Groups vo) throws SQLException {
        return false;
    }

    public boolean doUpdate(Groups vo) throws SQLException {
        String hql = "update Groups as g set g.title=?,g.note=? where g.gid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getTitle());
        query.setParameter(1,vo.getNote());
        query.setParameter(2,vo.getGid());
        return query.executeUpdate() > 0;
    }

    public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
        return false;
    }

    public Groups findById(Integer id) throws SQLException {
        return getSession().get(Groups.class,id);
    }

    public List<Groups> findAll() throws SQLException {
        return handleList(Groups.class);
    }

    public List<Groups> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return handleListSplit(Groups.class,currentPage,lineSize,column,keyWord);
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return handleCount("Groups",column,keyWord);
    }
}
