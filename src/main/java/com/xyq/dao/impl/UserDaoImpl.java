package com.xyq.dao.impl;

import com.xyq.dao.IUserDao;
import com.xyq.entity.User;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class UserDaoImpl extends AbstractDaoImpl implements IUserDao {

    public User findLogin(String username, String password) throws Exception {
        String hql = "from User where userid=? and password=? and lockflag = 0";
        Query query = getQuery(hql);
        query.setParameter(0,username);
        query.setParameter(1,password);
        return (User) query.uniqueResult();
    }

    public boolean doUpdatePassword(User vo) throws Exception {
        String hql = "update User as u set u.password=? where u.userid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getPassword());
        query.setParameter(1,vo.getUserid());
        return query.executeUpdate()>0;
    }

    public boolean doUpdateRole(User vo) throws Exception {
        String hql = "update User as u set u.role.rid=?,u.level=? where u.userid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getRole().getRid());
        query.setParameter(1,vo.getLevel());
        query.setParameter(2,vo.getUserid());
        return query.executeUpdate() > 0;
    }

    public boolean doUpdateInfo(User vo) throws Exception {
        String hql = "update User as u set u.name=?,u.phone=?,u.email=?,u.level=?,u.role.rid=? where u.userid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getName());
        query.setParameter(1,vo.getPhone());
        query.setParameter(2,vo.getEmail());
        query.setParameter(3,vo.getLevel());
        query.setParameter(4,vo.getRole().getRid());
        query.setParameter(5,vo.getUserid());
        return query.executeUpdate()>0;
    }

    public boolean doUpdateLock(Set<String> ids, Integer lock) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("update User set lockflag=").append(lock).append(" where userid in(");
        Iterator<String> iter = ids.iterator();
        while (iter.hasNext()){
            sb.append("'").append(iter.next()).append("',");
        }
        sb.delete(sb.length()-1,sb.length()).append(")");
        sb.append(" and level>1");
        Query query = getQuery(sb.toString());
        return query.executeUpdate() > 0;
    }

    public List<User> findAllUser(Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        String hql = "from User as u where u."+column+" like ? and u.level in(2,3)";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        query.setFirstResult((currentPage-1)*lineSize);
        query.setMaxResults(lineSize);
        return query.list();
    }

    public List<User> findAllAdmin(Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        String hql = "from User as u where u."+column+" like ? and u.level in(0,1)";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        query.setFirstResult((currentPage-1)*lineSize);
        query.setMaxResults(lineSize);
        return query.list();
    }

    public List<User> findAllUserByLock(Integer lockflag, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        String hql = "from User as u where u."+column+" like ? and u.level in(2,3) and u.lockflag=?";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        query.setParameter(1,lockflag);
        query.setFirstResult((currentPage-1)*lineSize);
        query.setMaxResults(lineSize);
        return query.list();
    }

    public Integer getAllUserCountByLock(Integer lockflag, String column, String keyWord) throws Exception {
        String hql = "select count(*) from User as u where u."+column+" like ? and u.level in(2,3) and u.lockflag=?";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        query.setParameter(1,lockflag);
        return ((Long)query.uniqueResult()).intValue();
    }

    public Integer getAllUserCount(String column, String keyWord) throws Exception {
        String hql = "select count(*) from User as u where u."+column+" like ? and u.level in(2,3)";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        return ((Long)query.uniqueResult()).intValue();
    }

    public Integer getAllAdminCount(String column, String keyWord) throws Exception {
        String hql = "select count(*) from User as u where u."+column+" like ? and u.level in(0,1)";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        return ((Long)query.uniqueResult()).intValue();
    }

    public boolean doCreate(User vo) throws SQLException {
        return getSession().save(vo) !=null;
    }

    public boolean doUpdate(User vo) throws SQLException {
        String hql = "update User as u set u.name=?,u.phone=?,u.email=?,u.photo=? where u.userid=?";
        Query query = getQuery(hql);
        query.setParameter(0,vo.getName());
        query.setParameter(1,vo.getPhone());
        query.setParameter(2,vo.getEmail());
        query.setParameter(3,vo.getPhoto());
        query.setParameter(4,vo.getUserid());
        return query.executeUpdate()>0;
    }

    public boolean doRemoveBatch(Set<String> ids) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from ").append("User").append(" where ").append("userid in").append("(");
        Iterator<?> iterator = ids.iterator();
        while (iterator.hasNext()){
            sb.append("'").append(iterator.next()).append("'").append(",");
        }
        sb.delete(sb.length()-1,sb.length()).append(")");
        sb.append(" and level>0");
        Query query = getQuery(sb.toString());
        return query.executeUpdate() > 0;
    }

    public User findById(String id) throws SQLException {
        return (User) getSession().get(User.class, id);
    }

    public List<User> findAll() throws SQLException {
        return null;
    }

    public List<User> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return null;
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return null;
    }
}
