package com.xyq.util.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 这个类作为Dao子类的公共父类,目的是简化重复代码的开发
 */
public abstract class AbstractDaoImpl {
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 返回SessionFactory,一般只有操作二级缓存的时候才会调用此操作
     * @return
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 设置SessionFactory类对象,是在子类构造方法注入的时候自动完成调用
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 负责提供当前可用的Session对象
     * @return
     */
    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    /**
     * 利用此方法取得Query对象
     * @param hql 要执行的hql语句
     * @return
     */
    public Query getQuery(String hql){
        return getSession().createQuery(hql);
    }

    /**
     * 利用此方法实现SQL查询操作
     * @param sql 要执行的sql语句
     * @return
     */
    public Query getSQLQuery(String sql){
        return getSession().createSQLQuery(sql);
    }

    /**
     * 利用此方法取得Criteria对象
     * @param cls 操作类型
     * @return
     */
    public Criteria getCriteria(Class<?> cls){
        return getSession().createCriteria(cls);
    }

    /**
     * 实现数据量统计的查询操作
     * @param pojoName 要查询的pojo类的名称
     * @param column 进行模糊查询的列名称
     * @param keyWord 查询关键字
     * @return 统计数据的行数
     */
    public Integer handleCount(String pojoName,String column,String keyWord){
        String hql = "select count(*) from "+pojoName+" as p where p."+column+" like ?";
        Query query = getQuery(hql);
        query.setParameter(0,"%"+keyWord+"%");
        return ((Long)query.uniqueResult()).intValue();
    }

    /**
     * 处理数据的分页显示查询操作
     * @param cls 要处理的pojo类的名字
     * @param currentPage 当前所在页
     * @param lineSize 每页显示的数据行数
     * @param column 模糊查询列
     * @param keyWord 模糊查询关键字
     * @return 查询数据以List集合返回
     */
    public List handleListSplit(Class<?> cls,Integer currentPage, Integer lineSize, String column, String keyWord){
        Criteria criteria = getCriteria(cls);
        criteria.add(Restrictions.like(column,"%"+keyWord+"%"));
        criteria.setFirstResult((currentPage-1)*lineSize);
        criteria.setMaxResults(lineSize);
        return criteria.list();
    }

    /**
     * 进行数据的全部列表操作哦
     * @param cls 要操作的pojo类型
     * @return 列表以List集合返回
     */
    public List handleList(Class<?> cls){
        Criteria criteria = getCriteria(cls);
        return criteria.list();
    }

    /**
     * 负责处理数据的删除操作
     * @param pojoName 要删除数据的pojo名称
     * @param idName 主键列的名称
     * @param ids 包含所有要删除的所有id
     * @return
     */
    public boolean handleRemoveBatch(String pojoName,String idName ,Set<?> ids){
        StringBuffer sb = new StringBuffer();
        sb.append("delete from ").append(pojoName).append(" where ").append(idName).append(" in (");
        Iterator<?> iterator = ids.iterator();
        while (iterator.hasNext()){
            sb.append("'").append(iterator.next()).append("'").append(",");
        }
        sb.delete(sb.length()-1,sb.length()).append(")");
        Query query = getQuery(sb.toString());
        return query.executeUpdate() > 0;
    }
}
