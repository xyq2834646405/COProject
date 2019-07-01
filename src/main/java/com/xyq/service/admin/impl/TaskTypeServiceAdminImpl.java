package com.xyq.service.admin.impl;

import com.xyq.dao.ITasktypeDao;
import com.xyq.entity.Tasktype;
import com.xyq.service.admin.ITasktypeServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTypeServiceAdminImpl implements ITasktypeServiceAdmin {
    @Autowired
    private ITasktypeDao taskTypeDao;

    public boolean insert(Tasktype vo) throws Exception {
        return taskTypeDao.doCreate(vo);
    }

    public boolean update(Tasktype vo) throws Exception {
        return taskTypeDao.doUpdate(vo);
    }

    public List<Tasktype> list() throws Exception {
        return taskTypeDao.findAll();
    }
}
