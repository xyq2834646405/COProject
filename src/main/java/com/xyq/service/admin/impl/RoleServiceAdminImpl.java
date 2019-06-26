package com.xyq.service.admin.impl;

import com.xyq.dao.IGroupsDao;
import com.xyq.dao.IRoleDao;
import com.xyq.entity.Role;
import com.xyq.service.admin.IRoleServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoleServiceAdminImpl implements IRoleServiceAdmin {
    @Autowired
    private IGroupsDao groupsDao;

    @Autowired
    private IRoleDao roleDao;

    public Map<String, Object> insertPre() throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("allGroups",groupsDao.findAll());
        return map;
    }

    public Map<String, Object> updatePre(int rid) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("allGroups",groupsDao.findAll());
        map.put("role",roleDao.findById(rid));
        map.put("gids",roleDao.findRoleGroups(rid));
        return map;
    }

    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("allRoles",roleDao.findAllSplit(currentPage,lineSize,column,keyWord));
        map.put("roleCount",roleDao.getAllCount(column,keyWord));
        return map;
    }

    public boolean update(Role vo) throws Exception {
        if (roleDao.findByTitleAndNotId(vo.getTitle(),vo.getRid())==null){
            return roleDao.doUpdate(vo);
        }
        return false;
    }

    public boolean insert(Role vo) throws Exception {
        if (roleDao.findByTitle(vo.getTitle())==null){
            return this.roleDao.doCreate(vo);
        }
        return false;
    }

    public boolean checkTitle(String title) throws Exception {
        return roleDao.findByTitle(title)==null;
    }

    public boolean checkTitle(String title, int rid) throws Exception {
        return roleDao.findByTitleAndNotId(title,rid)==null;
    }
}
