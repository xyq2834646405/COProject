package com.xyq.dao.impl;

import com.xyq.dao.IRoleDao;
import com.xyq.entity.Groups;
import com.xyq.entity.Role;
import com.xyq.util.dao.AbstractDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

@Component
public class RoleDaoImpl extends AbstractDaoImpl implements IRoleDao {
    public Role findByTitle(String title) throws Exception {
        String hql = "from Role as r where r.title=?";
        Query query = getQuery(hql);
        query.setParameter(0,title);
        return (Role) query.uniqueResult();
    }

    public Role findByTitleAndNotId(String title, Integer rid) throws Exception {
        String hql = "from Role as r where r.title=? and r.rid!=?";
        Query query = getQuery(hql);
        query.setParameter(0,title);
        query.setParameter(1,rid);
        return (Role) query.uniqueResult();
    }

    public Map<Integer, Boolean> findRoleGroups(Integer rid) throws Exception {
        Map<Integer,Boolean> map = new HashMap<Integer, Boolean>();
        String sql = "select gid from role_groups where rid = ?";
        Query query = getSQLQuery(sql);
        query.setParameter(0,rid);
        List list = query.list();
        Iterator iter = list.iterator();
        while (iter.hasNext()){
            Integer gid = (Integer) iter.next();
            map.put(gid,true);
        }
        return map;
    }

    public List<Role> findAllAdmin() throws Exception {
        String hql = "from Role as r where r.rid not in(4,5)";
        Query query = getQuery(hql);
        return query.list();
    }

    public boolean doCreate(Role vo) throws SQLException {
        return getSession().save(vo) != null;
    }

    public boolean doUpdate(Role vo) throws SQLException {
        boolean flag = true;
        try {
            Query deleteQuery = getSQLQuery("delete from role_groups where rid = " + vo.getRid());
            deleteQuery.executeUpdate();
            Iterator<Groups> iter = vo.getGroups().iterator();
            while(iter.hasNext()){
                Groups groups = iter.next();
                Query insertQuery = getSQLQuery("insert into role_groups(rid,gid) values(?,?)");
                insertQuery.setParameter(0,vo.getRid());
                insertQuery.setParameter(1,groups.getGid());
                insertQuery.executeUpdate();
            }
            Query updateQuery = getSQLQuery("update role set title=?,note=? where rid=?");
            updateQuery.setParameter(0,vo.getTitle());
            updateQuery.setParameter(1,vo.getNote());
            updateQuery.setParameter(2,vo.getRid());
            updateQuery.executeUpdate();
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }

    public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
        return false;
    }

    public Role findById(Integer id) throws SQLException {
        return getSession().get(Role.class,id);
    }

    public List<Role> findAll() throws SQLException {
        return handleList(Role.class);
    }

    public List<Role> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
        return handleListSplit(Role.class,currentPage,lineSize,column,keyWord);
    }

    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return handleCount("Role",column,keyWord);
    }
}
