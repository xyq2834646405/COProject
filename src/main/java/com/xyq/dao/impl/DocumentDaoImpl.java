package com.xyq.dao.impl;

import com.xyq.dao.IDocumentDao;
import com.xyq.entity.Document;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class DocumentDaoImpl extends AbstractDaoImpl implements IDocumentDao {
    public boolean doCreate(Document vo) throws SQLException {
        return getSession().save(vo)!=null;
    }

    public boolean doUpdate(Document vo) throws SQLException {
        String hql = "update Document set dtid=?,title=?,content=?,file=? where did=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getDoctype().getDtid());
        query.setParameter(1,vo.getTitle());
        query.setParameter(2,vo.getContent());
        query.setParameter(3,vo.getFile());
        query.setParameter(4,vo.getDid());
        return query.executeUpdate() > 0;
    }

    public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
        return handleRemoveBatch("Document","did",ids);
    }

    public Document findById(Integer id) throws SQLException {
        return getSession().get(Document.class,id);
    }

    public List<Document> findAll() throws SQLException {
        return handleList(Document.class);
    }

    public List<Document> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return handleListSplit(Document.class,currentPage,lineSize,column,keyWord);
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return handleCount("Document",column,keyWord);
    }

    public List<Document> findAllSplitByUser(String userid, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        Criteria criteria = getCriteria(Document.class);
        criteria.add(Restrictions.and(
                Restrictions.like(column,"%"+keyWord+"%"),
                Restrictions.eq("user.userid",userid)
        ));
        criteria.setFirstResult((currentPage-1)*lineSize);
        criteria.setMaxResults(lineSize);
        return criteria.list();
    }

    public Integer getAllCountByUser(String userid, String column, String keyWord) throws Exception {
        String hql = "select count(*) from Document as d where d."+column+" like ? and d.user.userid=?";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        query.setParameter(1,userid);
        return ((Long)query.uniqueResult()).intValue();
    }

    public boolean doRemoveBatchByUser(String userid, Set<Integer> ids) throws Exception {
        StringBuffer buf = new StringBuffer();
        buf.append("delete from Document where did in(");
        Iterator<Integer> iter = ids.iterator();
        while(iter.hasNext()){
            buf.append(iter.next()).append(",");
        }
        buf.delete(buf.length()-1,buf.length()).append(")");
        buf.append(" and userid='").append(userid).append("'");
        Query query = getQuery(buf.toString());
        return query.executeUpdate() > 0;
    }
}
