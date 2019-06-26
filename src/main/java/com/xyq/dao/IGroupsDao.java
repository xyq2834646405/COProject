package com.xyq.dao;

import com.xyq.entity.Groups;
import com.xyq.util.dao.IDao;

import java.util.List;

public interface IGroupsDao  extends IDao<Integer, Groups> {
    /**
     * 利用子查询查询一个角色对应的所有权限组信息,这样的做法:避免掉Hibernate中自动级联时多余的查询已经性能低下的多表查询
     * @param rid 角色编号
     * @return 一个角色具备的所有权限组信息
     * @throws Exception
     */
    public List<Groups> findAllByRole(Integer rid) throws Exception;
}
