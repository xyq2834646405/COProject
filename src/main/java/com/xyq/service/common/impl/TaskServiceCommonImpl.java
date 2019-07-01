package com.xyq.service.common.impl;

import com.xyq.dao.ITaskDao;
import com.xyq.entity.Task;
import com.xyq.service.common.ITaskServiceCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceCommonImpl implements ITaskServiceCommon {
    @Autowired
    private ITaskDao taskDao;

    public Task show(int id) throws Exception {
        Task task = taskDao.findById(id);
        if(task!=null){
            task.getProject().getTitle();
            task.getTasktype().getTitle();
        }
        return task;
    }
}
