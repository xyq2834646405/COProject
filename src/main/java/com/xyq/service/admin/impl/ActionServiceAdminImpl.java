package com.xyq.service.admin.impl;

import com.xyq.dao.IActionDao;
import com.xyq.entity.Action;
import com.xyq.service.admin.IActionServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActionServiceAdminImpl implements IActionServiceAdmin {

    @Autowired
    private IActionDao actionDao;

    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("allActions",actionDao.findAllSplit(currentPage,lineSize,column,keyWord));
        map.put("actionCount",actionDao.getAllCount(column,keyWord));
        return map;
    }

    public boolean update(Action vo) throws Exception {
        return actionDao.doUpdate(vo);
    }
}
