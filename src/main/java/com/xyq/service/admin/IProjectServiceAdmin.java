package com.xyq.service.admin;

import com.xyq.entity.Project;

import java.util.Map;

public interface IProjectServiceAdmin {
    /**
     * 在进行项目的信息增加前需要查询出以下内容:
     * <li>调用IUserDao.findAllByLevel(2)查询出所有的项目经理</li>
     * @return 返回的数据集合里面包含有以下内容:
     * <li>key = allManagers、value = IUserDao.findAllByLevel()</li>
     * @throws Exception
     */
    public Map<String,Object> insertPre() throws Exception;

    /**
     * 实现项目数据的增加操作,需要执行如下功能:
     * <li>调用IUserDao.findById()查询是否是管理员发布</li>
     * <li>调用IProjectDao.doCreate()保存项目信息</li>
     * @param vo 包含了项目数据的VO对象
     * @return 增加成功返回true,否则返回false
     * @throws Exception
     */
    public boolean insert(Project vo) throws Exception;

    /**
     * 根据项目的编号查询出项目完整信息,更新前使用,包含如下操作步骤
     * <li>调用IProjectDao.findById()方法取得一个项目信息</li>
     * <li>调用IUserDao.findAllByLevel(2)取得所有的项目经理信息</li>
     * @param id 项目编号
     * @return 返回的数据集合里面包含有以下内容:
     * <li>key = project、value = IProjectDao.findById()</li>
     * <li>key = allManagers、value = IUserDao.findAllByLevel()</li>
     * @throws Exception
     */
    public Map<String,Object> updatePre(int id) throws Exception;

    /**
     * 实现项目数据的更新操作,需要执行如下功能:
     * <li>调用IUserDao.findById()查询是否是管理员发布</li>
     * <li>调用IProjectDao.doUpdate()更新项目信息</li>
     * @param vo
     * @return 更新成功返回true,否则返回false
     * @throws Exception
     */
    public boolean update(Project vo) throws Exception;

    /**
     * 分页显示出所有的项目信息,包含如下操作:
     * <li>调用IProjectDao.findAllSplit()方法查询出全部的项目内容</li>
     * <li>调用IProjectDao.getAllCount()方法统计项目的个数</li>
     * @param currentPage 当前页
     * @param lineSize 每页显示的行数
     * @param column 模糊查询列
     * @param keyWord 模糊查询关键字
     * @return 返回的集合包含如下数据:
     * <li>key = allProjects、value = IProjectDao.findAllSplit()</li>
     * <li>key = projectCount、value = IProjectDao.getAllCount()</li>
     * @throws Exception
     */
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception;

}
