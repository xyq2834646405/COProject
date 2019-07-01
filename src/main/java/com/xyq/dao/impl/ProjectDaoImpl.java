package com.xyq.dao.impl;

import com.xyq.dao.IProjectDao;
import com.xyq.entity.Project;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class ProjectDaoImpl extends AbstractDaoImpl implements IProjectDao {
    public boolean doCreate(Project vo) throws SQLException {
        return getSession().save(vo)!=null;
    }

    public boolean doUpdate(Project vo) throws SQLException {
        String hql = "update Project as p set p.title=?,p.note=?,p.userByMgr.userid=?,p.name=? where p.proid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getTitle());
        query.setParameter(1,vo.getNote());
        query.setParameter(2,vo.getUserByMgr().getUserid());
        query.setParameter(3,vo.getName());
        query.setParameter(4,vo.getProid());
        return query.executeUpdate() > 0;
    }

    public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
        return false;
    }

    public Project findById(Integer id) throws SQLException {
        return getSession().get(Project.class,id);
    }

    public List<Project> findAll() throws SQLException {
        return null;
    }

    public List<Project> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return handleListSplit(Project.class,currentPage,lineSize,column,keyWord);
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return handleCount("Project",column,keyWord);
    }
}
