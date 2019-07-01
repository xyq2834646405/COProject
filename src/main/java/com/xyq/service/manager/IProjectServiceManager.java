package com.xyq.service.manager;

import com.xyq.entity.Project;

import java.util.Map;

public interface IProjectServiceManager {
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
    public Map<String,Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception;

    /**
     * 项目信息的显示操作,调用IProjectDao.findById()方法
     * @param id
     * @return
     * @throws Exception
     */
    public Project show(int id) throws Exception;
}
