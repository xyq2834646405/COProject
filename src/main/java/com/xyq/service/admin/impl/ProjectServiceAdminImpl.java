package com.xyq.service.admin.impl;

import com.xyq.dao.IProjectDao;
import com.xyq.dao.IUserDao;
import com.xyq.entity.Project;
import com.xyq.entity.User;
import com.xyq.service.admin.IProjectServiceAdmin;
import com.xyq.util.dao.AbstractDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectServiceAdminImpl extends AbstractDaoImpl implements IProjectServiceAdmin {
    @Autowired
    private IProjectDao projectDao;
    @Autowired
    private IUserDao userDao;

    public Map<String, Object> insertPre() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allManagers",userDao.findAllByLevel(2));
        return map;
    }

    public boolean insert(Project vo) throws Exception {
        User admin = userDao.findById(vo.getUserByCreid().getUserid());
        if (admin.getLevel()<2){
            User manager = userDao.findById(vo.getUserByMgr().getUserid());
            if(manager.getLevel()==2){
                vo.setPubdate(new Date());
                vo.setName(manager.getName());
                return projectDao.doCreate(vo);
            }
        }
        return false;
    }

    public Map<String, Object> updatePre(int id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allManagers",userDao.findAllByLevel(2));
        map.put("project",projectDao.findById(id));
        return map;
    }

    public boolean update(Project vo) throws Exception {
        User admin = userDao.findById(vo.getUserByCreid().getUserid());
        if (admin.getLevel() < 2) {
            User manager = userDao.findById(vo.getUserByMgr().getUserid());
            if (manager.getLevel() == 2) {
                vo.setName(manager.getName());
                return projectDao.doUpdate(vo);
            }
        }
        return false;
    }

    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allProjects",projectDao.findAllSplit(currentPage,lineSize,column,keyWord));
        map.put("projectCount",projectDao.getAllCount(column,keyWord));
        return map;
    }
}
