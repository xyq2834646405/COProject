package com.xyq.service.emp.impl;

import com.xyq.dao.IProjectDao;
import com.xyq.entity.Project;
import com.xyq.service.emp.IProjectServiceEmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectServiceEmpImpl implements IProjectServiceEmp {
    @Autowired
    private IProjectDao projectDao;

    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allProjects",projectDao.findAllSplit(currentPage,lineSize,column,keyWord));
        map.put("projectCount",projectDao.getAllCount(column,keyWord));
        return map;
    }

    public Project show(int id) throws Exception {
        return projectDao.findById(id);
    }
}
