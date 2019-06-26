package com.xyq.dao;

import com.xyq.entity.Action;
import com.xyq.util.dao.IDao;

import java.util.List;

public interface IActionDao extends IDao<Integer, Action> {
    /**
     * 根据权限组的编号查询所有对应的权限信息
     * @param gid 权限组编号
     * @return 返回权限信息
     * @throws Exception
     */
    public List<Action> findAllByGroups(Integer gid) throws Exception;
}
