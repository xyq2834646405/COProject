package com.xyq.service.emp.impl;

import com.xyq.dao.IProjectDao;
import com.xyq.dao.ITaskDao;
import com.xyq.entity.Task;
import com.xyq.service.emp.ITaskServiceEmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskServiceEmpImpl implements ITaskServiceEmp {
    @Autowired
    private IProjectDao projectDao;
    @Autowired
    private ITaskDao taskDao;

    public Map<String, Object> listByProject(int proid, int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("project",projectDao.findById(proid));
        map.put("allTasks",taskDao.findAllByProject(proid,currentPage,lineSize,column,keyWord));
        map.put("taskCount",taskDao.getAllCountByProject(proid,column,keyWord));
        return map;
    }

    public boolean updateFinish(Task vo) throws Exception {
        Task task = taskDao.findById(vo.getTid());
        if (task.getUserByReceiver().getUserid().equals(vo.getUserByReceiver().getUserid())) {
            if(task.getStatus()<2){
                vo.setFinishdate(new Date());
                vo.setStatus(3);
                return taskDao.doUpdateFinish(vo);
            }
        }
        return false;
    }

    public Task show(int id, String userid) throws Exception {
        Task task = taskDao.findById(id);
        if (task!=null){
            if(task.getUserByReceiver().getUserid().equals(userid)){//为当前任务
                taskDao.doUpdateStatus(id,1);//正在进行
            }
            task.getProject().getTitle();
            task.getTasktype().getTitle();
        }
        return task;
    }

    public Map<String, Object> listByReceiver(String userid, int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("allTasks",taskDao.findAllByEmp(userid,currentPage,lineSize,column,keyWord));
        map.put("taskCount",taskDao.getAllCountByEmp(userid,column,keyWord));
        return map;

    }
}
