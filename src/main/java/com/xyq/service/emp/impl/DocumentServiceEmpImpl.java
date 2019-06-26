package com.xyq.service.emp.impl;

import com.xyq.dao.IDoctypeDao;
import com.xyq.dao.IDocumentDao;
import com.xyq.entity.Document;
import com.xyq.service.emp.IDocumentServiceEmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class DocumentServiceEmpImpl implements IDocumentServiceEmp {

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
        Document document = documentDao.findById(vo.getDid());
        if (document.getUser().getUserid().equals(vo.getUser().getUserid())){
            return documentDao.doUpdate(vo);
        }
        return false;
    }

    public boolean delete(Set<Integer> ids, String userid) throws Exception {
        if(ids.size() == 0)
            return false;
        return documentDao.doRemoveBatchByUser(userid,ids);
    }

    public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allDoctypes",doctypeDao.findAll());
        map.put("allDocuments",documentDao.findAllSplit(currentPage,lineSize,column,keyWord));
        map.put("documentCount",documentDao.getAllCount(column,keyWord));
        return map;
    }

    public Map<String, Object> listByUser(String userid, int currentPage, int lineSize, String column, String keyWord) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("allDoctypes",doctypeDao.findAll());
        map.put("allDocuments",documentDao.findAllSplitByUser(userid,currentPage,lineSize,column,keyWord));
        map.put("documentCount",documentDao.getAllCountByUser(userid,column,keyWord));
        return map;
    }

    public Document show(int did) throws Exception {
        Document document = documentDao.findById(did);
        document.getDoctype().getTitle();//加载文档类型
        return document;
    }
}
