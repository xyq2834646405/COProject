package com.xyq.action.common;

import com.xyq.entity.Document;
import com.xyq.service.common.IDocumentServiceCommon;
import com.xyq.util.action.AbstractAction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;

@Repository
@ParentPackage("root")
@Namespace("/pages/jsp/common/document")
@Action("DocumentActionCommon")
@InterceptorRef("commonStack")
public class DocumentActionCommon extends AbstractAction {
    private Document document = new Document();

    @Autowired
    private IDocumentServiceCommon documentServiceCommon;

    public void show(){
        try {
            Document document = documentServiceCommon.show(this.document.getDid());
            JSONObject obj = new JSONObject();
            obj.put("did",document.getDid());
            obj.put("title",document.getTitle());
            obj.put("pubdate",formatDate(document.getPubdate()));
            obj.put("content",document.getContent());
            obj.put("file",document.getFile());
            obj.put("userid",document.getUser().getUserid());
            obj.put("doctype",document.getDoctype().getTitle());
            print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDefaultColumn() {
        return null;
    }

    public String getColumnData() {
        return null;
    }

    public String getTypeName() {
        return null;
    }

    public Document getDocument() {
        return document;
    }
}
