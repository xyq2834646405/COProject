package com.xyq.service.emp;

import com.xyq.entity.Task;

import java.util.Map;

public interface ITaskServiceEmp {
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

    /**
     * 实现任务完成的回单填写,要执行如下功能:
     * <li>首先要判断当前的完成用户是否是任务规定的用户</li>
     * <li>要判断当前的任务状态是什么,只有0或1的时候才可以更新数据</li>
     * <li>执行更新回单的操作方法</li>
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateFinish(Task vo) throws Exception;

    /**
     * 显示一个任务的详细信息,同时更新状态
     * @param id 要读取的任务
     * @param userid 要操作的用户
     * @return
     * @throws Exception
     */
    public Task show(int id,String userid) throws Exception;

    /**
     * 根据任务执行者的ID,查找出他所创建的所有任务信息
     * @param userid 管理员的用户id
     * @param currentPage 当前页
     * @param lineSize 每页显示的行数
     * @param column 模糊查询列
     * @param keyWord 模糊查询关键字
     * @return 返回的数据如下:
     * <li>key = allTasks、value = ITaskDao.findAllByEmp()</li>
     * <li>key = taskCount、value = ITaskDao.getAllCountByEmp()</li>
     * @throws Exception
     */
    public Map<String,Object> listByReceiver(String userid,int currentPage,int lineSize,String column,String keyWord) throws Exception;
}
