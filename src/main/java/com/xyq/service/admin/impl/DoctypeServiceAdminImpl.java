package com.xyq.service.admin.impl;

import com.xyq.dao.IDoctypeDao;
import com.xyq.entity.Doctype;
import com.xyq.service.admin.IDoctypeServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctypeServiceAdminImpl implements IDoctypeServiceAdmin {

    @Autowired
    private IDoctypeDao doctypeDao;

    public boolean insert(Doctype vo) throws Exception {
        return doctypeDao.doCreate(vo);
    }

    public boolean update(Doctype vo) throws Exception {
        return doctypeDao.doUpdate(vo);
    }

    public List<Doctype> list() throws Exception {
        return doctypeDao.findAll();
    }
}
