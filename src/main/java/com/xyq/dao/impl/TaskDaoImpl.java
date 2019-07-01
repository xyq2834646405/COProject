package com.xyq.dao.impl;

import com.xyq.dao.ITaskDao;
import com.xyq.entity.Task;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
public class TaskDaoImpl extends AbstractDaoImpl implements ITaskDao {
    public boolean doCreate(Task vo) throws SQLException {
        return getSession().save(vo)!=null;
    }

    public boolean doUpdate(Task vo) throws SQLException {
        return false;
    }

    public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
        return false;
    }

    public Task findById(Integer id) throws SQLException {
        return getSession().get(Task.class,id);
    }

    public List<Task> findAll() throws SQLException {
        return null;
    }

    public List<Task> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return null;
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return null;
    }

    public List<Task> findAllByProject(Integer proid, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        Criteria criteria = getCriteria(Task.class);
        criteria.add(Restrictions.and(
                Restrictions.like(column, "%" + keyWord + "%") ,
                Restrictions.eq("project.proid", proid)));
        criteria.setFirstResult((currentPage - 1) * lineSize);
        criteria.setMaxResults(lineSize);
        return criteria.list();
    }

    public Integer getAllCountByProject(Integer proid, String column, String keyWord) throws Exception {
        String hql = "select count(*) from Task as t where t."+column+" like ? and t.project.proid=?";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        query.setParameter(1,proid);
        return ((Long)query.uniqueResult()).intValue();
    }

    public boolean doUpdateCancel(Task vo) throws Exception {
        String hql = "update Task as t set t.userByCanceler.userid=?,t.cnote=?,t.status=? where t.tid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getUserByCreator().getUserid());
        query.setParameter(1,vo.getCnote());
        query.setParameter(2,vo.getStatus());
        query.setParameter(3,vo.getTid());
        return query.executeUpdate() > 0;
    }

    public boolean doUpdateInfo(Task vo) throws Exception {
        String hql = "update Task as t set t.userByReceiver.userid=?,t.title=?,t.lastupdatedate=?,t.priority=?,t.estimate=?,t.note=?,t.tasktype.ttid=?,t.expiredate=? where t.tid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getUserByReceiver().getUserid());
        query.setParameter(1,vo.getTitle());
        query.setParameter(2,vo.getLastupdatedate());
        query.setParameter(3,vo.getPriority());
        query.setParameter(4,vo.getEstimate());
        query.setParameter(5,vo.getNote());
        query.setParameter(6,vo.getTasktype().getTtid());
        query.setParameter(7,vo.getExpiredate());
        query.setParameter(8,vo.getTid());
        return query.executeUpdate() > 0;
    }

    public boolean doUpdateFinish(Task vo) throws Exception {
        String hql = "update Task as t set t.expend=?,t.status=?,t.finishdate=?,t.rnote=? where t.tid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getExpend());
        query.setParameter(1,vo.getStatus());
        query.setParameter(2,vo.getFinishdate());
        query.setParameter(3,vo.getRnote());
        query.setParameter(4,vo.getTid());
        return query.executeUpdate() > 0;
    }

    public boolean doUpdateStatus(Integer id, Integer status) throws Exception {
        String hql = "update Task as t set t.status=? where t.tid=?";
        Query query = getQuery(hql);
        query.setParameter(0,status);
        query.setParameter(1,id);
        return query.executeUpdate() > 0;
    }

    public List<Task> findAllByManager(String userid, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        Criteria criteria = getCriteria(Task.class);
        criteria.add(Restrictions.and(
                Restrictions.like(column, "%" + keyWord + "%") ,
                Restrictions.eq("userByCreator.userid", userid)));
        criteria.setFirstResult((currentPage - 1) * lineSize);
        criteria.setMaxResults(lineSize);
        return criteria.list();
    }

    public Integer getAllCountByManager(String userid, String column, String keyWord) throws Exception {
        String hql = "select count(*) from Task as t where t."+column+" like ? and t.userByCreator.userid=?";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        query.setParameter(1,userid);
        return ((Long)query.uniqueResult()).intValue();
    }

    public List<Task> findAllByEmp(String userid, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        Criteria criteria = getCriteria(Task.class);
        criteria.add(Restrictions.and(
                Restrictions.like(column, "%" + keyWord + "%") ,
                Restrictions.eq("userByReceiver.userid", userid)));
        criteria.setFirstResult((currentPage - 1) * lineSize);
        criteria.setMaxResults(lineSize);
        return criteria.list();
    }

    public Integer getAllCountByEmp(String userid, String column, String keyWord) throws Exception {
        String hql = "select count(*) from Task as t where t."+column+" like ? and t.userByReceiver.userid=?";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        query.setParameter(1,userid);
        return ((Long)query.uniqueResult()).intValue();
    }

    public Integer getAllCountByStatus(String userid, Integer status) throws Exception {
        String hql = "select count(*) from Task as t where t.status=? and t.userByReceiver.userid=?";
        Query query = getQuery(hql);
        query.setParameter(0,status);
        query.setParameter(1,userid);
        return ((Long)query.uniqueResult()).intValue();
    }
}
