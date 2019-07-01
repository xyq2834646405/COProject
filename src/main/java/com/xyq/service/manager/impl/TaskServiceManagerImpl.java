package com.xyq.service.manager.impl;

import com.xyq.dao.IProjectDao;
import com.xyq.dao.ITaskDao;
import com.xyq.dao.ITasktypeDao;
import com.xyq.dao.IUserDao;
import com.xyq.entity.Project;
import com.xyq.entity.Task;
import com.xyq.entity.User;
import com.xyq.service.manager.ITaskServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskServiceManagerImpl implements ITaskServiceManager {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IProjectDao projectDao;
    @Autowired
    private ITaskDao taskDao;
    @Autowired
    private ITasktypeDao tasktypeDao;

    public Map<String, Object> insertPre() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allUsers",userDao.findAllByLevel(3));
        map.put("allTasktypes",tasktypeDao.findAll());
        return map;
    }

    public boolean insert(Task vo) throws Exception {
        //1、首先判断项目的负责人信息,那么要找到项目的原始信息
        Project project = projectDao.findById(vo.getProject().getProid());
        //2、要判断项目的负责人信息
        if(vo.getUserByCreator().getUserid().equals(project.getUserByMgr().getUserid())){
            //3、查询项目负责人的状态
            if(userDao.findById(vo.getUserByCreator().getUserid()).getLockflag()==0){//未锁定状态
                //判断任务的接收者用户状态
                if(userDao.findById(vo.getUserByReceiver().getUserid()).getLockflag()==0){
                    vo.setCreatedate(new Date());//设置创建日期
                    vo.setLastupdatedate(new Date());//创建日期为当前最后一次修改日期
                    vo.setStatus(0);
                    return taskDao.doCreate(vo);
                }
            }
        }
        return false;
    }

    public Map<String, Object> listByProject(int proid, int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("project",projectDao.findById(proid));
        map.put("allTasks",taskDao.findAllByProject(proid,currentPage,lineSize,column,keyWord));
        map.put("taskCount",taskDao.getAllCountByProject(proid,column,keyWord));
        return map;
    }

    public boolean updateCancel(Task vo) throws Exception {
        Project project = projectDao.findById(vo.getProject().getProid());
        if (vo.getUserByCreator().getUserid().equals(project.getUserByMgr().getUserid())){//取消者是项目的管理者
            vo.setStatus(2);//2为取消状态
            return taskDao.doUpdateCancel(vo);
        }
        return false;
    }

    public Map<String, Object> updatePre(int tid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allUsers",userDao.findAllByLevel(3));
        map.put("allTasktypes",tasktypeDao.findAll());
        map.put("task",taskDao.findById(tid));
        return map;
    }

    public boolean update(Task vo) throws Exception {
        //1、首先判断项目的负责人信息,那么要找到项目的原始信息
        Project project = projectDao.findById(vo.getProject().getProid());
        //2、要判断项目的负责人信息
        if(vo.getUserByCreator().getUserid().equals(project.getUserByMgr().getUserid())){
            //3、查询项目负责人的状态
            if(userDao.findById(vo.getUserByCreator().getUserid()).getLockflag()==0){//未锁定状态
                //判断任务的接收者用户状态
                if(userDao.findById(vo.getUserByReceiver().getUserid()).getLockflag()==0){
                    vo.setLastupdatedate(new Date());
                    return taskDao.doUpdateInfo(vo);
                }
            }
        }
        return false;
    }

    public Map<String, Object> listByCreator(String userid, int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("allTasks",taskDao.findAllByEmp(userid,currentPage,lineSize,column,keyWord));
        map.put("taskCount",taskDao.getAllCountByEmp(userid,column,keyWord));
        return map;
    }
}
