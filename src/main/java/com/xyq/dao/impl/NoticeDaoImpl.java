package com.xyq.dao.impl;

import com.xyq.dao.INoticeDao;
import com.xyq.entity.Notice;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

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

    public Map<Integer, Boolean> findUnread(String userid,Integer level) throws Exception {
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        String sql = "select snid from notice where snid not in(select snid from user_notice where userid=?) and level>=?";
        Query query = getSQLQuery(sql);
        query.setParameter(0,userid);
        query.setParameter(1,level);
        List<Object> result = query.list();
        Iterator<Object> iter = result.iterator();
        while(iter.hasNext()){
            map.put((Integer) iter.next(),true);
        }
        return map;
    }

    public Integer getAllCountUnread(String userid, Integer level) throws Exception {
        String sql = "select count(*) from notice where snid not in(select snid from user_notice where userid=?) and level>=?";
        Query query = getSQLQuery(sql);
        query.setParameter(0,userid);
        query.setParameter(1,level);
        return ((BigInteger)query.uniqueResult()).intValue();
    }

    public Notice findByIdAndLevel(Integer id, Integer level) throws Exception {
        String hql = "from Notice as n where n.snid=? and n.level>=?";
        Query query = getQuery(hql);
        query.setParameter(0,id);
        query.setParameter(1,level);
        return (Notice) query.uniqueResult();
    }

    public List<Notice> findAllSplitByLevel(Integer level, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        Criteria criteria = getCriteria(Notice.class);
        criteria.add(Restrictions.and(
                Restrictions.like(column,"%"+keyWord+"%"),
                Restrictions.ge("level",level)));
        criteria.setFirstResult((currentPage-1)*lineSize);
        criteria.setMaxResults(lineSize);
        return criteria.list();
    }

    public Integer getAllCountByLevel(Integer level, String column, String keyWord) throws Exception {
        String hql = "select count(*) from Notice as n where n."+column+" like ? and level>=?";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        query.setParameter(1,level);
        return ((Long)query.uniqueResult()).intValue();
    }
}
