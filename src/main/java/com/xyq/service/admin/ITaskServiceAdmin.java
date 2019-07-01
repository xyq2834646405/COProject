package com.xyq.service.admin;

import java.util.Map;

public interface ITaskServiceAdmin {
    /**
     * 根据项目查询出项目对应的所以也信息
     * @param proid 要查询的所有任务的项目编号
     * @param currentPage 当前页
     * @param lineSize 每页显示的行数
     * @param column 模糊查询列
     * @param keyWord 模糊查询关键字
     * @return 返回的数据如下:
     * <li>key = allTasks、value = ITaskDao.findAllByProject()</li>
     * <li>key = taskCount、value = ITaskDao.getAllCountByProject()</li>
     * <li>key = project、value = IProjectDao.findById()</li>
     * @throws Exception
     */
    public Map<String,Object> listByProject(int proid, int currentPage, int lineSize, String column, String keyWord) throws Exception;
}
