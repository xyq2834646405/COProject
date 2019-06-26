package com.xyq.service.admin.impl;

import com.xyq.dao.IDoctypeDao;
import com.xyq.dao.IDocumentDao;
import com.xyq.entity.Document;
import com.xyq.service.admin.IDocumentServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class DocumentServiceAdminImpl implements IDocumentServiceAdmin {
    @Autowired
    private IDocumentDao documentDao;

    @Autowired
    private IDoctypeDao doctypeDao;

    public Map<String, Object> insertPre() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allDoctypes",doctypeDao.findAll());
        return map;
    }

    public boolean insert(Document vo) throws Exception {
        vo.setPubdate(new Date());
        return documentDao.doCreate(vo);
    }

    public Map<String, Object> updatePre(int did) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allDoctypes",doctypeDao.findAll());
        map.put("document",documentDao.findById(did));
        return map;
    }

    public boolean update(Document vo) throws Exception {
        return documentDao.doUpdate(vo);
    }

    public boolean delete(Set<Integer> ids) throws Exception {
        if(ids.size() == 0)
            return false;
        return documentDao.doRemoveBatch(ids);
    }

    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allDoctypes",doctypeDao.findAll());
        map.put("allDocuments",documentDao.findAllSplit(currentPage,lineSize,column,keyWord));
        map.put("documentCount",documentDao.getAllCount(column,keyWord));
        return map;
    }
}
