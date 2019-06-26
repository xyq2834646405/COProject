package com.xyq.service.admin;

import com.xyq.entity.Groups;

import java.util.Map;

public interface IGroupsServiceAdmin {
    /**
     * 进行权限组的列表操作,要执行如下功能
     * 1、调用IGroupsDao.findAllSplit()取出全部的分页数据
     * 2、调用IGroupsDao.getAllCount()取出全部的数据量
     * @param currentPage 当前所有页
     * @param lineSize 每页显示的数据行
     * @param column 模糊查询列
     * @param keyWord 模糊查询关键字
     * @return 以Map集合返回,返回的数据如下:
     * 1、key = allGroups,value = IGroupsDao.findAllSplit()
     * 2、key = groupsCount,value = IGroupsDao.getAllCount()
     * @throws Exception
     */
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception;

    /**
     * 实现权限组的更新操作,调用IGroupsDao.doUpdate()
     * @param vo 要更新VO数据
     * @return 更新成功返回true,否则返回false
     * @throws Exception
     */
    public boolean update(Groups vo) throws Exception;

    /**
     * 显示一个权限组的完整内容,要求同时查出此权限组对应的所有权限信息
     * 在查询权限信息的时候可以利用对象的持久态完成
     * @param gid 权限组的编号
     * @return 如果查询到内容则以POJO对象返回,否则返回null
     * @throws Exception
     */
    public Groups show(int gid) throws Exception;
}
