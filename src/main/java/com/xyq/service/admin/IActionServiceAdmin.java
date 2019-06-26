package com.xyq.service.admin;

import com.xyq.entity.Action;

import java.util.Map;

public interface IActionServiceAdmin {
    /**
     * 数据的列表显示操作,使用分页显示,要调用如下功能
     * 1、调用IActionDao.findAllBySplit()操作取得全部的数据信息
     * 2、调用IActionDao.getAllCount()操作取得全部数据量
     * @param currentPage 当前所有页
     * @param lineSize 每页显示的数据行
     * @param column 模糊查询列
     * @param keyWord 模糊查询关键字
     * @return 返回的数据主要包含两个内容,以Map集合返回,组成如下:
     * 1、key = allActions、value = IActionDao.findAllSplit()
     * 2、key = actionCount、value = IActionDao.getAllCount()
     * @throws Exception
     */
    public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord)throws Exception;

    /**
     * 进行权限的数据更新操作
     * @param vo 包含有新权限内容的VO对象
     * @return 更新成功返回true,否则返回false
     * @throws Exception
     */
    public boolean update(Action vo) throws Exception;
}
