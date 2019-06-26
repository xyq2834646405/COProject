package com.xyq.service.admin.impl;

import com.xyq.dao.IGroupsDao;
import com.xyq.entity.Groups;
import com.xyq.service.admin.IGroupsServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GroupsServiceAdminImpl implements IGroupsServiceAdmin {
    @Autowired
    private IGroupsDao groupsDao;

    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("allGroups",groupsDao.findAllSplit(currentPage,lineSize,column,keyWord));
        map.put("groupsCount",groupsDao.getAllCount(column,keyWord));
        return map;
    }

    public boolean update(Groups vo) throws Exception {
        return groupsDao.doUpdate(vo);
    }

    public Groups show(int gid) throws Exception {
        Groups vo = groupsDao.findById(gid);
//        if(vo!=null){//默认的延迟加载是打开的
//            vo.getActions().size();//执行此操作就表示要加载"多"方数据
//        }
        return vo;
    }
}
