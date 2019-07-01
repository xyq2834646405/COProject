package com.xyq.service.admin.impl;

import com.xyq.dao.ITaskDao;
import com.xyq.service.admin.ITaskServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskServiceAdminImpl implements ITaskServiceAdmin {
    @Autowired
    private ITaskDao taskDao;

    public Map<String, Object> listByProject(int proid, int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("allTasks",taskDao.findAllByProject(proid,currentPage,lineSize,column,keyWord));
        map.put("taskCount",taskDao.getAllCountByProject(proid,column,keyWord));
        return map;
    }
}
