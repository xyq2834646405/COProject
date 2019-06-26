package com.xyq.service.common.impl;

import com.xyq.dao.IDocumentDao;
import com.xyq.entity.Document;
import com.xyq.service.common.IDocumentServiceCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceCommonImpl implements IDocumentServiceCommon {
    @Autowired
    private IDocumentDao documentDao;

    public Document show(int did) throws Exception {
        Document document = documentDao.findById(did);
        document.getDoctype().getTitle();//利用hibernate的持久太记载文档类型
        return document;
    }
}
